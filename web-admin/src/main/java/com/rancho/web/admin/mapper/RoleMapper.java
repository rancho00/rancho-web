package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper{

    List<Role> getRoles(Role role);

    Role getRole(Integer id);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Integer id);

    List<Role> listByAdminId(@Param("adminId") Integer adminId);
}
