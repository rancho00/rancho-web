package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.dto.admin.AdminPassword;
import com.rancho.web.db.domain.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    Admin getAdminByUsername(@Param("username") String username);

    AdminPassword getAdminPasswordByUsername(@Param("username") String username);

    List<Admin> getAdmins(Admin admin);

    void addAdmin(AdminPassword adminPassword);

    Admin getAdmin(Integer id);

    void updateAdmin(Admin admin);

    void updateAdminPassword(@Param("id") Integer id,@Param("password") String password);

    void updateAdminStatus(Integer id,Integer status);
}
