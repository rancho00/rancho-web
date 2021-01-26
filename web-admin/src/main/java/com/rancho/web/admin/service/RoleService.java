package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.Admin;
import com.rancho.web.admin.domain.Role;
import com.rancho.web.admin.domain.RoleMenu;
import com.rancho.web.admin.domain.dto.role.RoleCreate;
import com.rancho.web.admin.domain.dto.role.RoleUpdate;
import com.rancho.web.common.page.Page;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<String> getAdminRoles(Admin admin);

    List<Role> getRoles(Role role, Page page);

    List<Role> getAllRoles();

    void addRole(RoleCreate roleCreate);

    void updateRole(Integer id, RoleUpdate roleUpdate);

    void updateRoleMenu(Integer id, List<Integer> menusIds);

    void deleteRole(Integer id);

    List<RoleMenu> getRoleMenus(Integer roleId);
}
