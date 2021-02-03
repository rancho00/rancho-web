package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.serve.ServeCreate;
import com.rancho.web.admin.domain.serve.ServeParam;
import com.rancho.web.admin.domain.serve.ServeUpdate;
import com.rancho.web.admin.mapper.ServeMapper;
import com.rancho.web.admin.service.ServeService;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Serve;
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
public class ServeServiceImpl implements ServeService {

    @Autowired
    private ServeMapper serveMapper;

    @Override
    public List<Serve> getServes(ServeParam serveParam, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<Serve> serves=serveMapper.getServes(serveParam);
        return serves;
    }

    @Override
    public void addServe(ServeCreate serveCreate) {
        Serve serve=new Serve();
        BeanUtils.copyProperties(serveCreate,serve);
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        serve.setCreateAdmin(adminUserDetails.getUsername());
        serve.setCreateTime(new Date());
        serveMapper.addServe(serve);
    }

    @Override
    public void updateServe(Integer id, ServeUpdate serveUpdate) {
        Serve serve=new Serve();
        serve.setId(id);
        BeanUtils.copyProperties(serveUpdate,serve);
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        serve.setUpdateAdmin(adminUserDetails.getUsername());
        serve.setUpdateTime(new Date());
        serveMapper.updateServe(serve);
    }

    @Override
    public void deleteServe(Integer[] ids) {
        serveMapper.deleteServe(ids);
    }

    @Override
    public List<Map<String, Object>> getServeOptions() {
        List<Map<String, Object>> serverOptions=serveMapper.getServes(null).stream().map(server -> {
            Map<String,Object> map=new HashMap<>();
            map.put("value",server.getId());
            map.put("label",server.getName());
            return map;
        }).collect(Collectors.toList());
        return serverOptions;
    }
}
