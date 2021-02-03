package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.server.ServerConnect;
import com.rancho.web.admin.domain.server.ServerCreate;
import com.rancho.web.admin.domain.server.ServerUpdate;
import com.rancho.web.admin.domain.server.ServerParam;
import com.rancho.web.admin.mapper.ServerMapper;
import com.rancho.web.admin.service.ServerService;
import com.rancho.web.admin.util.ShellUtil;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Server;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerMapper serverMapper;

    @Override
    public List<Server> getServers(ServerParam serverParam, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return serverMapper.getServers(serverParam);
    }

    @Override
    public void addServer(ServerCreate serverCreate) {
        Server server=new Server();
        BeanUtils.copyProperties(serverCreate,server);
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        server.setCreateAdmin(adminUserDetails.getUsername());
        server.setCreateTime(new Date());
        serverMapper.addServer(server);
    }

    @Override
    public void updateServer(Integer id,ServerUpdate serverUpdate) {
        Server server=new Server();
        server.setId(id);
        BeanUtils.copyProperties(serverUpdate,server);
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        server.setUpdateAdmin(adminUserDetails.getUsername());
        server.setUpdateTime(new Date());
        serverMapper.updateServer(server);
    }

    @Override
    public void deleteServer(Integer[] ids) {
        serverMapper.deleteServer(ids);
    }

    @Override
    public boolean connect(ServerConnect connect) {
        ShellUtil executeShellUtil = null;
        try {
            executeShellUtil = new ShellUtil(connect.getIp(), connect.getAccount(), connect.getPassword(),connect.getPort());
            return executeShellUtil.execute("ls")==0;
        } catch (Exception e) {
            return false;
        }finally {
            if (executeShellUtil != null) {
                executeShellUtil.close();
            }
        }
    }

    @Override
    public List<Map<String, Object>> getServerOptions() {
        List<Map<String, Object>> serverOptions=serverMapper.getServers(null).stream().map(server -> {
            Map<String,Object> map=new HashMap<>();
            map.put("value",server.getId());
            map.put("label",server.getName());
            return map;
        }).collect(Collectors.toList());
        return serverOptions;
    }
}
