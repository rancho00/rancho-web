package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.dto.adminDto.AdminBaseDto;
import com.rancho.web.admin.domain.dto.adminDto.AdminLoginDto;
import com.rancho.web.common.page.Page;

import java.util.List;

public interface SmsAdminService {

    String login(AdminLoginDto adminLoginDto);

    List<SmsAdmin> list(SmsAdmin smsAdmin,Page page);

    void save(AdminBaseDto adminBaseDto);

    SmsAdmin getById(Integer id);

    void update(AdminBaseDto adminBaseDto);

    void updateStatus(Integer id,Integer status);

    SmsAdmin getByUsername(String username);

    AdminBaseDto getAdminBaseDtoById(Integer id);
}
