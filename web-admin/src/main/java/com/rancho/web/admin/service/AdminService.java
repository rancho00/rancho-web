package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.Admin;
import com.rancho.web.admin.domain.dto.adminDto.AdminCreate;
import com.rancho.web.admin.domain.dto.adminDto.AdminLogin;
import com.rancho.web.admin.domain.dto.adminDto.AdminUpdate;
import com.rancho.web.common.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AdminService {

    String login(AdminLogin adminLogin);

    List<Admin> getAdmins(Admin admin, Page page);

    void addAdmin(AdminCreate adminCreate);

    Admin getAdmin(Integer id);

    void updateAdmin(Integer id,AdminUpdate adminUpdate);

    void updateAdminState(Integer id,Integer status);

    Admin getAdminByUsername(String username);

    //AdminCreate getAdminBaseById(Integer id);

    void download(List<Admin> adminList, HttpServletResponse response) throws IOException;
}
