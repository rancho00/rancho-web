package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.Admin;
import com.rancho.web.admin.domain.dto.adminDto.AdminCreate;
import com.rancho.web.admin.domain.dto.adminDto.AdminLogin;
import com.rancho.web.admin.domain.dto.adminDto.AdminUpdate;
import com.rancho.web.admin.domain.vo.AdminWithRole;
import com.rancho.web.common.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AdminService {

    String login(AdminLogin adminLogin);

    void logout(Admin admin);

    Admin getAdminByUsername(String username);

    List<Admin> getAdmins(Admin admin, Page page);

    AdminWithRole getAdminWithRole(Integer id);

    void addAdmin(AdminCreate adminCreate);

    void updateAdmin(Integer id,AdminUpdate adminUpdate);

    void updateAdminStatus(Integer id,Integer status);

    void download(List<Admin> adminList, HttpServletResponse response) throws IOException;
}
