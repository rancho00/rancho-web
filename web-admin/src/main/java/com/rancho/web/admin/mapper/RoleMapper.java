package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper{

    List<Role> getRoles(Role role);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Integer id);

    Set<String> getAdminRoleKeys(@Param("adminId") Integer adminId);
}
