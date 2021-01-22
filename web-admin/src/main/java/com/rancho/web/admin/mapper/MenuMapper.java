package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper{

    List<Menu> listRoleMenus(@Param("roleId") Integer roleId);

    List<Menu> listAdminMenus(@Param("adminId") Integer adminId);
}
