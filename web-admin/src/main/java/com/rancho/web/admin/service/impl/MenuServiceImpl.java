package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.dto.menu.MenuCreate;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.admin.mapper.MenuMapper;
import com.rancho.web.admin.mapper.RoleMenuMapper;
import com.rancho.web.admin.service.MenuService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl  implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuNode> getTreeMenus() {
        List<Menu> menuList= null;//menuMapper.list(null);
        List<MenuNode> menuNodeList=menuList.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menuList))
                .collect(Collectors.toList());

        return menuNodeList;
    }

    @Override
    public List<MenuNode> getAdminTreeMenus(Integer adminId) {
        //查询目录
        List<Menu> menuList = menuMapper.listAdminMenus(adminId);
        List<MenuNode> menuNodeList=menuList.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menuList))
                .collect(Collectors.toList());

        return menuNodeList;
    }

    @Override
    public List<Menu> getMenus(Menu menu, Page page) {
        //setPage(page);
        List<Menu> menuList = null;//menuMapper.list(menu);
        return menuList;
    }

    @Override
    public void addMenu(MenuCreate menuCreate) {
        //menuMapper.save(menu);
    }

    @Override
    public void updateMenu(Integer id, Menu menu) {
        //menuMapper.update(menu);
    }

    @Override
    public void updateMenuStatus(Integer id, Integer status) {
        //查询是否关联角色
        int roleMenuCount= roleMenuMapper.countByMenuId(id);
        if(roleMenuCount>0){
            throw new CommonException("菜单关联了角色，请先解除关联关系！");
        }
        //修改状态
        Menu menu =new Menu();
        menu.setId(id);
        menu.setStatus(status);
        //menuMapper.update(menu);
    }

    @Override
    public void deleteMenu(Integer id) {
        //查询是否关联角色
        int roleMenuCount= roleMenuMapper.countByMenuId(id);
        if(roleMenuCount>0){
            throw new CommonException("菜单关联了角色，请先解除关联关系！");
        }
        //删除
        //menuMapper.delete(id);
    }

    @Override
    public Menu getMenu(Integer id) {
        //return menuMapper.getById(id);
        return null;
    }

    @Override
    public List<Menu> getRoleMenus(Integer roleId) {
        List<Menu> menuList = menuMapper.listRoleMenus(roleId);
        return menuList;
    }

    @Override
    public List<Menu> getAdminMenus(Integer adminId) {
        return menuMapper.listAdminMenus(adminId);
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
}
