package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    Admin getAdminByUsername(@Param("username") String username);

    List<Admin> getAdmins(Admin admin);

    void addAdmin(Admin admin);

    Admin getAdmin(Integer id);

    void updateAdmin(Admin admin);

    void updateAdminStatus(Integer id,Integer status);
}
