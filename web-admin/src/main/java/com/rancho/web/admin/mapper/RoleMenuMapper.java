package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuMapper{

    void deleteByRoleId(@Param("roleId")Integer roleId);

    int countByMenuId(@Param("menuId")Integer menuId);

    void addRoleMenu(RoleMenu roleMenu);
}
