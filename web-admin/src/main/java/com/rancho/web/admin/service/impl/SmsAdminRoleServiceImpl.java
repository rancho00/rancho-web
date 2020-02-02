package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsAdminRole;
import com.rancho.web.admin.mapper.SmsAdminRoleMapper;
import com.rancho.web.admin.service.SmsAdminRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsAdminRoleServiceImpl implements SmsAdminRoleService {

    @Resource
    private SmsAdminRoleMapper smsAdminRoleMapper;

    @Override
    public List<SmsAdminRole> list(SmsAdminRole smsAdminRole) {
        return smsAdminRoleMapper.list(smsAdminRole);
    }
}
