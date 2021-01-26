package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper{

    void addAdminRole(AdminRole adminRole);

    int getAdminRoleCountByRoleId(@Param("roleId") Integer roleId);

    void deleteByAdminId(@Param("adminId") Integer adminId);

    List<AdminRole> getAdminRole(AdminRole adminRole);

    List<AdminRole> getAdminRoleByAdminId(@Param("adminId") Integer adminId);
}
