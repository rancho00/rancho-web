package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.dto.menu.MenuParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper{

    List<Menu> getAllMenus();

    List<Menu> getMenus(MenuParam menuParam);

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    Menu getMenu(@Param("id") Integer id);

    void deleteMenu(@Param("id") Integer id);

    List<Menu> listRoleMenus(@Param("roleId") Integer roleId);

    List<Menu> getAdminMenus(@Param("adminId") Integer adminId);

    Set<String> getAdminMenuPermissions(@Param("adminId")Integer adminId);

}
