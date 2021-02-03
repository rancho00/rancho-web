package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.admin.mapper.DeployMapper;
import com.rancho.web.admin.mapper.DeployServerMapper;
import com.rancho.web.admin.mapper.ServerMapper;
import com.rancho.web.admin.service.DeployService;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Deploy;
import com.rancho.web.db.domain.DeployServer;
import com.rancho.web.db.domain.Server;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeployServiceImpl implements DeployService {

    @Autowired
    private DeployMapper deployMapper;

    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private DeployServerMapper deployServerMapper;

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
    public void deploy(String fileSavePath, Integer id) {
//        AppDto app = deploy.getApp();
//        if (app == null) {
//            sendMsg("包对应应用信息不存在", MsgType.ERROR);
//            throw new BadRequestException("包对应应用信息不存在");
//        }
//        int port = app.getPort();
//        //这个是服务器部署路径
//        String uploadPath = app.getUploadPath();
//        StringBuilder sb = new StringBuilder();
//        String msg;
//        Set<ServerDeployDto> deploys = deploy.getDeploys();
//        for (ServerDeployDto deployDTO : deploys) {
//            String ip = deployDTO.getIp();
//            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(ip);
//            //判断是否第一次部署
//            boolean flag = checkFile(executeShellUtil, app);
//            //第一步要确认服务器上有这个目录
//            executeShellUtil.execute("mkdir -p " + app.getUploadPath());
//            executeShellUtil.execute("mkdir -p " + app.getBackupPath());
//            executeShellUtil.execute("mkdir -p " + app.getDeployPath());
//            //上传文件
//            msg = String.format("登陆到服务器:%s", ip);
//            ScpClientUtil scpClientUtil = getScpClientUtil(ip);
//            log.info(msg);
//            sendMsg(msg, MsgType.INFO);
//            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", ip, uploadPath);
//            sendMsg(msg, MsgType.INFO);
//            scpClientUtil.putFile(fileSavePath, uploadPath);
//            if (flag) {
//                sendMsg("停止原来应用", MsgType.INFO);
//                //停止应用
//                stopApp(port, executeShellUtil);
//                sendMsg("备份原来应用", MsgType.INFO);
//                //备份应用
//                backupApp(executeShellUtil, ip, app.getDeployPath()+FILE_SEPARATOR, app.getName(), app.getBackupPath()+FILE_SEPARATOR, id);
//            }
//            sendMsg("部署应用", MsgType.INFO);
//            //部署文件,并启动应用
//            String deployScript = app.getDeployScript();
//            executeShellUtil.execute(deployScript);
//            sleep(3);
//            sendMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", MsgType.INFO);
//            int i  = 0;
//            boolean result = false;
//            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
//            while (i++ < count){
//                result = checkIsRunningStatus(port, executeShellUtil);
//                if(result){
//                    break;
//                }
//                // 休眠6秒
//                sleep(6);
//            }
//            sb.append("服务器:").append(deployDTO.getName()).append("<br>应用:").append(app.getName());
//            sendResultMsg(result, sb);
//            executeShellUtil.close();
    }
}
