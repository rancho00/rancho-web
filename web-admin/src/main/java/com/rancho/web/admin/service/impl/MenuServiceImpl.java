package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.dto.menu.MenuCreate;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.admin.domain.dto.menu.MenuUpdate;
import com.rancho.web.admin.domain.vo.MetaVo;
import com.rancho.web.admin.domain.vo.RouteVo;
import com.rancho.web.admin.mapper.MenuMapper;
import com.rancho.web.admin.mapper.RoleMenuMapper;
import com.rancho.web.admin.service.MenuService;
import com.rancho.web.db.domain.Admin;
import com.rancho.web.db.domain.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl  implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuNode> getTreeMenus() {
        List<Menu> menuList= menuMapper.getMenus(null);
        List<MenuNode> menuNodeList=menuList.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menuList))
                .collect(Collectors.toList());
        return menuNodeList;
    }

    @Override
    public void addMenu(MenuCreate menuCreate) {
        Menu menu=new Menu();
        BeanUtils.copyProperties(menuCreate,menu);
        if(menu.getType()==0){
            menu.setComponent("Layout");
        }
        if(menu.getType()==2){
            menu.setIsHidden(1);
        }else{
            menu.setIsHidden(0);
        }
        menuMapper.addMenu(menu);
    }

    @Override
    public Menu getMenu(Integer id) {
        return menuMapper.getMenu(id);
    }

    @Override
    public void updateMenu(Integer id, MenuUpdate menuUpdate) {
        Menu menu=new Menu();
        BeanUtils.copyProperties(menuUpdate,menu);
        menuMapper.updateMenu(menu);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteMenu(id);
    }

    @Override
    public List<MenuNode> getAdminTreeMenus(Admin admin) {
        List<Menu> menus;
        if("admin".equals(admin.getType())){
            menus = menuMapper.getAllMenus();
        }else{
            menus = menuMapper.getAdminMenus(admin.getId());
        }
        List<MenuNode> menuNodes=menus.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menus))
                .collect(Collectors.toList());
        return menuNodes;
    }

    @Override
    @Cacheable(value = "adminPermission",key = "#admin.id")
    public Set<String> getAdminMenuPermissions(Admin admin) {
        Set<String> adminPermissions=new HashSet<>();
        if("admin".equals(admin.getType())){
            Set<String> permissions=menuMapper.getAllMenus().stream().map(Menu::getPermission).collect(Collectors.toSet());
            adminPermissions.addAll(permissions);
        }else{
            Set<String> permissions=menuMapper.getAdminMenuPermissions(admin.getId());
            adminPermissions.addAll(permissions);
        }
        return adminPermissions;
    }

    /**
     *
     * @param menu
     * @param menuList
     * @return
     */
    private MenuNode covert(Menu menu, List<Menu> menuList){
        MenuNode node = new MenuNode();
        BeanUtils.copyProperties(menu,node);
        List<Menu> children = menuList.stream()
                .filter(subMenu -> subMenu.getPid().equals(menu.getId()))
                .map(subMenu -> covert(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public List<RouteVo> menuCovertRoute(List<MenuNode> menuNodes) {
        List<RouteVo> routeVos =new ArrayList<>();
        for(MenuNode menuNode:menuNodes){
            RouteVo routeVo =new RouteVo();
            routeVo.setName(menuNode.getName());
            routeVo.setPath(menuNode.getUri());
            if(menuNode.getIsHidden()==0){
                routeVo.setHidden(false);
            }else{
                routeVo.setHidden(true);
            }

            //routerVo.setRedirect();
            routeVo.setComponent(menuNode.getComponent());
            //routerVo.setAlwaysShow();
            MetaVo metaVo=new MetaVo();
            metaVo.setIcon(menuNode.getIcon());
            metaVo.setTitle(menuNode.getName());
            routeVo.setMeta(metaVo);
            List<RouteVo> children=new ArrayList<>();

            for(Menu menu:menuNode.getChildren()){
                RouteVo nextRouteVo =new RouteVo();
                nextRouteVo.setName(menu.getName());
                nextRouteVo.setPath(menu.getUri());
                nextRouteVo.setComponent(menu.getComponent());
                if(menu.getIsHidden()==0){
                    nextRouteVo.setHidden(false);
                }else{
                    nextRouteVo.setHidden(true);
                }
                MetaVo nextMetaVo=new MetaVo();
                nextMetaVo.setIcon(menu.getIcon());
                nextMetaVo.setTitle(menu.getName());
                nextRouteVo.setMeta(nextMetaVo);
                children.add(nextRouteVo);
            }
            routeVo.setChildren(children);
            routeVos.add(routeVo);
        }
        return routeVos;
    }
}
