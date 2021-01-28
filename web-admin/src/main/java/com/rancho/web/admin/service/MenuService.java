package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.dto.menu.MenuCreate;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.admin.domain.dto.menu.MenuUpdate;
import com.rancho.web.admin.domain.vo.RouteVo;
import com.rancho.web.db.domain.Admin;
import com.rancho.web.db.domain.Menu;

import java.util.List;
import java.util.Set;

public interface MenuService {

    List<MenuNode> getTreeMenus();

    void addMenu(MenuCreate menuCreate);

    Menu getMenu(Integer id);

    void updateMenu(Integer id, MenuUpdate menuUpdate);

    void deleteMenu(Integer id);

    List<MenuNode> getAdminTreeMenus(Admin admin);

    Set<String> getAdminMenuPermissions(Admin admin);

    List<RouteVo> menuCovertRoute(List<MenuNode> menuNodes);
}
