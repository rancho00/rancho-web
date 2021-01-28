package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.dto.admin.AdminCreate;
import com.rancho.web.admin.domain.dto.admin.AdminLogin;
import com.rancho.web.admin.domain.dto.admin.AdminPassword;
import com.rancho.web.admin.domain.dto.admin.AdminUpdate;
import com.rancho.web.admin.domain.vo.AdminWithRole;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Admin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AdminService {

    String login(AdminLogin adminLogin);

    void updateLoginAdminPassword(String oldPassword,String newPassword);

    void logout(Admin admin);

    Admin getAdminByUsername(String username);

    AdminPassword getAdminPasswordByUsername(String username);

    List<Admin> getAdmins(Admin admin, Page page);

    AdminWithRole getAdminWithRole(Integer id);

    void addAdmin(AdminCreate adminCreate);

    void updateAdmin(Integer id,AdminUpdate adminUpdate);

    void updateAdminPassword(Integer id,String password);

    void updateAdminStatus(Integer id,Integer status);

    void download(List<Admin> adminList, HttpServletResponse response) throws IOException;
}
