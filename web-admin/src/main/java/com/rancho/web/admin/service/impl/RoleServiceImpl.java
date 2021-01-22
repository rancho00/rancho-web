package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.Role;
import com.rancho.web.admin.domain.RoleMenu;
import com.rancho.web.admin.domain.dto.role.RoleBase;
import com.rancho.web.admin.mapper.AdminRoleMapper;
import com.rancho.web.admin.mapper.RoleMapper;
import com.rancho.web.admin.mapper.RoleMenuMapper;
import com.rancho.web.admin.service.MenuService;
import com.rancho.web.admin.service.RoleService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    @Override
    public List<Role> getRolesByAdminId(Integer adminId) {
        return roleMapper.listByAdminId(adminId);
    }


    @Override
    public List<Role> getRoles(Role role, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return roleMapper.getRoles(role);
    }

    @Override
    public void addRole(RoleBase roleBase) {
        Role role =new Role();
        BeanUtils.copyProperties(roleBase, role);
        roleMapper.addRole(role);
        //添加角色菜单权限
        for(Integer menuId: roleBase.getMenuIdList()){
            RoleMenu roleMenu =new RoleMenu();
            roleMenu.setRoleId(role.getId());
            roleMenu.setMenuId(menuId);
            roleMenuMapper.addRoleMenu(roleMenu);
        }
    }

    @Override
    public RoleBase getRoleBaseById(Integer id) {
        Role role = roleMapper.getRole(id);
        RoleBase roleBase =new RoleBase();
        BeanUtils.copyProperties(role, roleBase);
        //加载权限菜单
        roleBase.setMenuIdList(menuService.getRoleMenus(id).stream().map(Menu::getId).collect(Collectors.toList()));
        return roleBase;
    }

    @Override
    public void updateRole(Integer id, RoleBase roleBase) {
        Role role =new Role();
        BeanUtils.copyProperties(roleBase, role);
        roleMapper.updateRole(role);
        //删除角色菜单权限
        roleMenuMapper.deleteByRoleId(role.getId());
        //添加角色菜单权限
        for(Integer menuId: roleBase.getMenuIdList()){
            RoleMenu roleMenu =new RoleMenu();
            roleMenu.setRoleId(role.getId());
            roleMenu.setMenuId(menuId);
            roleMenuMapper.addRoleMenu(roleMenu);
        }
    }

    @Override
    public void deleteRole(Integer id) {
        int count= adminRoleMapper.countByRoleId(id);
        if(count>0){
            throw new CommonException("该角色已有管理员关联，请先解除关联！");
        }
        roleMapper.deleteRole(id);
        roleMenuMapper.deleteByRoleId(id);
    }
}
