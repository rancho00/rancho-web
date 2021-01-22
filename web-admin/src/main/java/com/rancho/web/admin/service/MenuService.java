package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.dto.menu.MenuCreate;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.common.page.Page;

import java.util.List;

public interface MenuService {

    List<MenuNode> getTreeMenus();

    List<MenuNode> getAdminTreeMenus(Integer adminId);

    List<Menu> getRoleMenus(Integer roleId);

    List<Menu> getAdminMenus(Integer adminId);

    List<Menu> getMenus(Menu menu, Page page);

    void addMenu(MenuCreate menuCreate);

    void updateMenu(Integer id, Menu menu);

    void updateMenuStatus(Integer id,Integer status);

    void deleteMenu(Integer id);

    Menu getMenu(Integer id);
}
