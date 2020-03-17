package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.dto.adminDto.SmsAdminBase;
import com.rancho.web.admin.domain.dto.adminDto.SmsAdminLogin;
import com.rancho.web.common.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface SmsAdminService {

    String login(SmsAdminLogin smsAdminLogin);

    List<SmsAdmin> list(SmsAdmin smsAdmin,Page page);

    void save(SmsAdminBase smsAdminBase);

    SmsAdmin getById(Integer id);

    void update(SmsAdminBase smsAdminBase);

    void updateStatus(Integer id,Integer status);

    SmsAdmin getByUsername(String username);

    SmsAdminBase getAdminBaseById(Integer id);

    void download(List<SmsAdmin> smsAdminList, HttpServletResponse response) throws IOException;
}
