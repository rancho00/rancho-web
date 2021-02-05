package com.rancho.web.admin.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.admin.mapper.DeployHistoryMapper;
import com.rancho.web.admin.mapper.DeployMapper;
import com.rancho.web.admin.mapper.DeployServerMapper;
import com.rancho.web.admin.mapper.ServerMapper;
import com.rancho.web.admin.service.DeployService;
import com.rancho.web.admin.util.ExecuteShellUtil;
import com.rancho.web.admin.util.ScpClientUtil;
import com.rancho.web.admin.util.SecurityUtils;
import com.rancho.web.admin.websocket.MsgType;
import com.rancho.web.admin.websocket.SocketMsg;
import com.rancho.web.admin.websocket.WebSocketHolder;
import com.rancho.web.common.common.BadRequestException;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.result.ResultCode;
import com.rancho.web.db.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Slf4j
@Service
public class DeployServiceImpl implements DeployService {

    private final String FILE_SEPARATOR = "/";
    private final Integer count = 30;

    @Autowired
    private DeployMapper deployMapper;

    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private DeployServerMapper deployServerMapper;

    @Autowired
    private DeployHistoryMapper deployHistoryMapper;

    @Override
    public List<DeployInfo> getDeployInfos(Page page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<DeployInfo> deployInfos=deployMapper.getDeployInfos().stream().map(deployInfo -> {
            List<Server> servers=serverMapper.getServersByDeployId(deployInfo.getId());
            deployInfo.setServers(servers);
            return deployInfo;
        }).collect(Collectors.toList());
        return deployInfos;
    }

    @Override
    public List<DeployHistory> getDeployHistoryByDeployId(Integer deployId,Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<DeployHistory> deployHistories=deployHistoryMapper.getDeployHistoryByDeployId(deployId);
        return deployHistories;
    }

    @Override
    public DeployInfo getDeployInfo(Integer id){
        DeployInfo deployInfo = deployMapper.getDeployInfo(id);
        List<Server> servers=serverMapper.getServersByDeployId(deployInfo.getId());
        deployInfo.setServers(servers);
        return deployInfo;
    }

    @Override
    public void addDeploy(DeployCreate deployCreate) {
        Deploy deploy=new Deploy();
        deploy.setServeId(deployCreate.getServeId());
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        deploy.setCreateAdmin(adminUserDetails.getUsername());
        deploy.setCreateTime(new Date());
        deployMapper.addDeploy(deploy);

        Integer[] serverIds=deployCreate.getServerIds();
        Arrays.stream(serverIds).forEach(serverId->{
            DeployServer deployServer=new DeployServer();
            deployServer.setDeployId(deploy.getId());
            deployServer.setServerId(serverId);
            deployServerMapper.addDeployServer(deployServer);
        });
    }

    @Override
    public void updateDeploy(Integer id,DeployUpdate deployUpdate) {
        deployServerMapper.deleteDeployServerByDeployId(id);
        deployMapper.deleteDeploy(new Integer[]{id});

        Deploy deploy=new Deploy();
        deploy.setServeId(deployUpdate.getServeId());
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        deploy.setCreateAdmin(adminUserDetails.getUsername());
        deploy.setCreateTime(new Date());
        deployMapper.addDeploy(deploy);

        Integer[] serverIds=deployUpdate.getServerIds();
        Arrays.stream(serverIds).forEach(serverId->{
            DeployServer deployServer=new DeployServer();
            deployServer.setDeployId(deploy.getId());
            deployServer.setServerId(serverId);
            deployServerMapper.addDeployServer(deployServer);
        });
    }

    @Override
    public void deleteDeploy(Integer[] ids) {
        deployMapper.deleteDeploy(ids);
    }

    @Override
    public void deploy(Integer id,String fileSavePath) throws IOException, InterruptedException {

        DeployInfo deployInfo = getDeployInfo(id);
        if (deployInfo == null) {
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("部署信息不存在", MsgType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("部署信息不存在");
        }

        Serve serve = deployInfo.getServe();
        if (serve == null) {
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("包对应服务信息不存在", MsgType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("包对应服务信息不存在");
        }

        int port = serve.getPort();
        //这个是服务器部署路径
        StringBuilder sb = new StringBuilder();
        String msg;
        List<Server> servers = deployInfo.getServers();
        for (Server server : servers) {
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(server);
            //判断是否第一次部署
            boolean flag = checkFile(executeShellUtil, serve);
            //第一步要确认服务器上有这个目录
            executeShellUtil.execute("mkdir -p " + serve.getUploadPath());
            executeShellUtil.execute("mkdir -p " + serve.getBackupPath());
            executeShellUtil.execute("mkdir -p " + serve.getDeployPath());
            //上传文件
            msg = String.format("登陆到服务器:%s", server.getIp());
            ScpClientUtil scpClientUtil = getScpClientUtil(server);
            log.info(msg);
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(msg, MsgType.INFO));
            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", server.getIp(), serve.getUploadPath());
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(msg, MsgType.INFO));
            scpClientUtil.putFile(fileSavePath, serve.getUploadPath());
            if (flag) {
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(msg, MsgType.INFO));
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("停止原来应用", MsgType.INFO));
                //停止应用
                stopApp(port, executeShellUtil);
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("备份原来应用", MsgType.INFO));
                //备份应用
                backupApp(executeShellUtil, server.getIp(), serve.getDeployPath() + FILE_SEPARATOR, serve.getName(), serve.getBackupPath() + FILE_SEPARATOR, id);
            }
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("部署应用", MsgType.INFO));
            //部署文件,并启动应用
            String deployScript = serve.getDeployScript();
            executeShellUtil.execute(deployScript);
            sleep(3);
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", MsgType.INFO));
            int i = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count) {
                result = checkIsRunningStatus(port, executeShellUtil);
                if (result) {
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            sendResultMsg(result, sb);
            executeShellUtil.close();
        }
    }

    @Override
    public void restore(Integer historyId) throws InterruptedException, IOException{
        DeployHistory deployHistory=deployHistoryMapper.getDeployHistory(historyId);
        DeployInfo deployInfo = getDeployInfo(deployHistory.getDeployId());
        String deployDate = DateUtil.format(deployHistory.getCreateTime(), DatePattern.PURE_DATETIME_PATTERN);
        Serve serve = deployInfo.getServe();
        if (serve == null) {
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("应用信息不存在：" + deployHistory.getServeName(), MsgType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("应用信息不存在：" + deployHistory.getServeName());
        }
        String backupPath = serve.getBackupPath()+FILE_SEPARATOR;
        backupPath += deployHistory.getServeName() + FILE_SEPARATOR + deployDate;
        //这个是服务器部署路径
        String deployPath = serve.getDeployPath();
        String ip = deployHistory.getIp();
        ExecuteShellUtil executeShellUtil = getExecuteShellUtil(deployInfo.getServers().get(0));
        String msg;

        msg = String.format("登陆到服务器:%s", ip);
        log.info(msg);
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(msg, MsgType.INFO));
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("停止原来应用", MsgType.INFO));
        //停止应用
        stopApp(serve.getPort(), executeShellUtil);
        //删除原来应用
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("删除应用", MsgType.INFO));
        executeShellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + deployHistory.getServeName());
        //还原应用
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("还原应用", MsgType.INFO));
        executeShellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("启动应用", MsgType.INFO));
        executeShellUtil.execute(serve.getStartScript());
        WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态", MsgType.INFO));
        int i  = 0;
        boolean result = false;
        // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
        while (i++ < count){
            result = checkIsRunningStatus(serve.getPort(), executeShellUtil);
            if(result){
                break;
            }
            // 休眠6秒
            sleep(6);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("服务器:").append(ip).append("<br>应用:").append(deployHistory.getServeName());
        sendResultMsg(result, sb);
        executeShellUtil.close();
    }

    @Override
    public void serverStatus(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            boolean result = checkIsRunningStatus(serve.getPort(), executeShellUtil);
            if (result) {
                sb.append("<br>正在运行");
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.INFO));
            } else {
                sb.append("<br>已停止!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.INFO));
            }
            log.info(sb.toString());
            executeShellUtil.close();
        }
    }

    @Override
    public void startServer(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(server);
            //为了防止重复启动，这里先停止应用
            stopApp(serve.getPort(), executeShellUtil);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("下发启动命令", MsgType.INFO));
            executeShellUtil.execute(serve.getStartScript());
            sleep(3);
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态", MsgType.INFO));
            int i  = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count){
                result = checkIsRunningStatus(serve.getPort(), executeShellUtil);
                if(result){
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sendResultMsg(result, sb);
            log.info(sb.toString());
            executeShellUtil.close();
        }
    }

    @Override
    public void stopServer(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg("下发停止命令", MsgType.INFO));
            //停止应用
            stopApp(serve.getPort(), executeShellUtil);
            sleep(1);
            boolean result = checkIsRunningStatus(serve.getPort(), executeShellUtil);
            if (result) {
                sb.append("<br>关闭失败!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.ERROR));
            } else {
                sb.append("<br>关闭成功!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.INFO));
            }
            log.info(sb.toString());
            executeShellUtil.close();
        }
    }

    private ExecuteShellUtil getExecuteShellUtil(Server server) {
        return new ExecuteShellUtil(server.getIp(), server.getAccount(), server.getPassword(),server.getPort());
    }

    private boolean checkFile(ExecuteShellUtil executeShellUtil, Serve serve) {
        String result = executeShellUtil.executeForResult("find " + serve.getDeployPath() + " -name " + serve.getName());
        return result.indexOf(serve.getName())>0;
    }

    private ScpClientUtil getScpClientUtil(Server server) {
        return ScpClientUtil.getInstance(server.getIp(), server.getPort(), server.getAccount(), server.getPassword());
    }

    private void stopApp(int port, ExecuteShellUtil executeShellUtil) {
        executeShellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));

    }

    private void backupApp(ExecuteShellUtil executeShellUtil, String ip, String fileSavePath, String appName, String backupPath, Integer id) {
        String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        StringBuilder sb = new StringBuilder();
        backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
        sb.append("mkdir -p ").append(backupPath);
        sb.append("mv -f ").append(fileSavePath);
        sb.append(appName).append(" ").append(backupPath);
        log.info("备份应用脚本:" + sb.toString());
        executeShellUtil.execute(sb.toString());
        //还原信息入库
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setServeName(appName);
        deployHistory.setCreateAdmin(SecurityUtils.getUsername());
        deployHistory.setIp(ip);
        deployHistory.setDeployId(id);
        deployHistoryMapper.addDeployHistory(deployHistory);
    }

    private boolean checkIsRunningStatus(int port, ExecuteShellUtil executeShellUtil) {
        String result = executeShellUtil.executeForResult(String.format("fuser -n tcp %d", port));
        return result.indexOf("/tcp:")>0;
    }

    private void sendResultMsg(boolean result, StringBuilder sb) throws IOException {
        if (result) {
            sb.append("<br>启动成功!");
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.INFO));
        } else {
            sb.append("<br>启动失败!");
            WebSocketHolder.send(SecurityUtils.getUsername(), new SocketMsg(sb.toString(), MsgType.ERROR));
        }
    }

}
