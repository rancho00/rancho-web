package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper{

    void deleteRoleMenu(@Param("roleId")Integer roleId);

    void addRoleMenu(RoleMenu roleMenu);

    List<RoleMenu> getRoleMenusByRoleId(@Param("roleId")Integer roleId);
}
