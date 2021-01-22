package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.Role;
import com.rancho.web.admin.domain.dto.role.RoleBase;
import com.rancho.web.common.page.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {

    List<Role> getRoles(Role role, Page page);

    @Transactional
    void addRole(RoleBase roleBase);

    @Transactional
    void updateRole(Integer id, RoleBase roleBase);

    void deleteRole(Integer id);

    List<Role> getRolesByAdminId(Integer adminId);

    RoleBase getRoleBaseById(Integer id);
}
