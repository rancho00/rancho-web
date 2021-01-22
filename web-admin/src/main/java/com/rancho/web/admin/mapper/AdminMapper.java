package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    List<Admin> getAdmins(Admin admin);

    Admin getAdminByUsername(@Param("username") String username);

    void addAdmin(Admin admin);

    Admin getAdmin(Integer id);

    void updateAdmin(Admin admin);

    void updateAdminState(Integer id,Integer status);
}
