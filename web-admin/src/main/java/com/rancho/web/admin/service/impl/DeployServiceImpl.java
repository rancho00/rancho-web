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
import com.rancho.web.admin.util.ScpClientUtil;
import com.rancho.web.admin.util.SecurityUtils;
import com.rancho.web.admin.util.ShellUtil;
import com.rancho.web.admin.websocket.BoxType;
import com.rancho.web.admin.websocket.BoxMsg;
import com.rancho.web.admin.websocket.WebSocketHolder;
import com.rancho.web.common.common.BadRequestException;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.result.ResultCode;
import com.rancho.web.db.domain.*;
import lombok.extern.slf4j.Slf4j;
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
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("部署信息不存在", BoxType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("部署信息不存在");
        }

        Serve serve = deployInfo.getServe();
        if (serve == null) {
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("包对应服务信息不存在", BoxType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("包对应服务信息不存在");
        }

        int port = serve.getPort();
        //这个是服务器部署路径
        StringBuilder sb = new StringBuilder();
        String msg;
        List<Server> servers = deployInfo.getServers();
        for (Server server : servers) {
            ShellUtil ShellUtil = getShellUtil(server);
            //判断是否第一次部署
            boolean flag = checkFile(ShellUtil, serve);
            //第一步要确认服务器上有这个目录
            ShellUtil.execute("mkdir -p " + serve.getUploadPath());
            ShellUtil.execute("mkdir -p " + serve.getBackupPath());
            ShellUtil.execute("mkdir -p " + serve.getDeployPath());
            //上传文件
            msg = String.format("登陆到服务器:%s", server.getIp());
            ScpClientUtil scpClientUtil = getScpClientUtil(server);
            log.info(msg);
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(msg, BoxType.INFO));
            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", server.getIp(), serve.getUploadPath());
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(msg, BoxType.INFO));
            scpClientUtil.putFile(fileSavePath, serve.getUploadPath());
            if (flag) {
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(msg, BoxType.INFO));
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("停止原来应用", BoxType.INFO));
                //停止应用
                stopApp(port, ShellUtil);
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("备份原来应用", BoxType.INFO));
                //备份应用
                backupApp(ShellUtil, server.getIp(), serve.getDeployPath() + FILE_SEPARATOR, serve.getName(), serve.getBackupPath() + FILE_SEPARATOR, id);
            }
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("部署应用", BoxType.INFO));
            //部署文件,并启动应用
            String deployScript = serve.getDeployScript();
            ShellUtil.execute(deployScript);
            sleep(3);
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", BoxType.INFO));
            int i = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count) {
                result = checkIsRunningStatus(port, ShellUtil);
                if (result) {
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            sendResultMsg(result, sb);
            ShellUtil.close();
        }
    }

    @Override
    public void restore(Integer historyId) throws InterruptedException, IOException{
        DeployHistory deployHistory=deployHistoryMapper.getDeployHistory(historyId);
        DeployInfo deployInfo = getDeployInfo(deployHistory.getDeployId());
        String deployDate = DateUtil.format(deployHistory.getCreateTime(), DatePattern.PURE_DATETIME_PATTERN);
        Serve serve = deployInfo.getServe();
        if (serve == null) {
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("应用信息不存在：" + deployHistory.getServeName(), BoxType.ERROR));
            throw new BadRequestException(ResultCode.BAD_REQUEST).message("应用信息不存在：" + deployHistory.getServeName());
        }
        String backupPath = serve.getBackupPath()+FILE_SEPARATOR;
        backupPath += deployHistory.getServeName() + FILE_SEPARATOR + deployDate;
        //这个是服务器部署路径
        String deployPath = serve.getDeployPath();
        String ip = deployHistory.getIp();
        ShellUtil ShellUtil = getShellUtil(deployInfo.getServers().get(0));
        String msg;

        msg = String.format("登陆到服务器:%s", ip);
        log.info(msg);
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(msg, BoxType.INFO));
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("停止原来应用", BoxType.INFO));
        //停止应用
        stopApp(serve.getPort(), ShellUtil);
        //删除原来应用
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("删除应用", BoxType.INFO));
        ShellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + deployHistory.getServeName());
        //还原应用
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("还原应用", BoxType.INFO));
        ShellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("启动应用", BoxType.INFO));
        ShellUtil.execute(serve.getStartScript());
        WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态", BoxType.INFO));
        int i  = 0;
        boolean result = false;
        // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
        while (i++ < count){
            result = checkIsRunningStatus(serve.getPort(), ShellUtil);
            if(result){
                break;
            }
            // 休眠6秒
            sleep(6);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("服务器:").append(ip).append("<br>应用:").append(deployHistory.getServeName());
        sendResultMsg(result, sb);
        ShellUtil.close();
    }

    @Override
    public void serverStatus(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ShellUtil ShellUtil = getShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            boolean result = checkIsRunningStatus(serve.getPort(), ShellUtil);
            if (result) {
                sb.append("<br>正在运行");
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.INFO));
            } else {
                sb.append("<br>已停止!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.INFO));
            }
            log.info(sb.toString());
            ShellUtil.close();
        }
    }

    @Override
    public void startServer(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ShellUtil ShellUtil = getShellUtil(server);
            //为了防止重复启动，这里先停止应用
            stopApp(serve.getPort(), ShellUtil);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("下发启动命令", BoxType.INFO));
            ShellUtil.execute(serve.getStartScript());
            sleep(3);
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态", BoxType.INFO));
            int i  = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count){
                result = checkIsRunningStatus(serve.getPort(), ShellUtil);
                if(result){
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sendResultMsg(result, sb);
            log.info(sb.toString());
            ShellUtil.close();
        }
    }

    @Override
    public void stopServer(Integer id) throws InterruptedException, IOException{
        DeployInfo deployInfo = getDeployInfo(id);
        List<Server> servers = deployInfo.getServers();
        Serve serve = deployInfo.getServe();
        for (Server server : servers) {
            StringBuilder sb = new StringBuilder();
            ShellUtil ShellUtil = getShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(serve.getName());
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg("下发停止命令", BoxType.INFO));
            //停止应用
            stopApp(serve.getPort(), ShellUtil);
            sleep(1);
            boolean result = checkIsRunningStatus(serve.getPort(), ShellUtil);
            if (result) {
                sb.append("<br>关闭失败!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.ERROR));
            } else {
                sb.append("<br>关闭成功!");
                WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.INFO));
            }
            log.info(sb.toString());
            ShellUtil.close();
        }
    }

    private ShellUtil getShellUtil(Server server) {
        return new ShellUtil(server.getIp(), server.getAccount(), server.getPassword(),server.getPort());
    }

    private boolean checkFile(ShellUtil ShellUtil, Serve serve) {
        String result = ShellUtil.executeForString("find " + serve.getDeployPath() + " -name " + serve.getName());
        return result.indexOf(serve.getName())>0;
    }

    private ScpClientUtil getScpClientUtil(Server server) {
        return ScpClientUtil.getInstance(server.getIp(), server.getPort(), server.getAccount(), server.getPassword());
    }

    private void stopApp(int port, ShellUtil ShellUtil) {
        ShellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));

    }

    private void backupApp(ShellUtil ShellUtil, String ip, String fileSavePath, String appName, String backupPath, Integer id) {
        String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        StringBuilder sb = new StringBuilder();
        backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
        sb.append("mkdir -p ").append(backupPath);
        sb.append("mv -f ").append(fileSavePath);
        sb.append(appName).append(" ").append(backupPath);
        log.info("备份应用脚本:" + sb.toString());
        ShellUtil.execute(sb.toString());
        //还原信息入库
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setServeName(appName);
        deployHistory.setCreateAdmin(SecurityUtils.getUsername());
        deployHistory.setIp(ip);
        deployHistory.setDeployId(id);
        deployHistoryMapper.addDeployHistory(deployHistory);
    }

    private boolean checkIsRunningStatus(int port, ShellUtil ShellUtil) {
        String result = ShellUtil.executeForString(String.format("fuser -n tcp %d", port));
        return result.indexOf("/tcp:")>0;
    }

    private void sendResultMsg(boolean result, StringBuilder sb) throws IOException {
        if (result) {
            sb.append("<br>启动成功!");
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.INFO));
        } else {
            sb.append("<br>启动失败!");
            WebSocketHolder.send(SecurityUtils.getUsername(), new BoxMsg(sb.toString(), BoxType.ERROR));
        }
    }

}
