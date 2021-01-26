package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.Admin;
import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.Role;
import com.rancho.web.admin.domain.RoleMenu;
import com.rancho.web.admin.domain.dto.role.RoleCreate;
import com.rancho.web.admin.domain.dto.role.RoleUpdate;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Set<String> getAdminRoles(Admin admin) {
        Set<String> adminRoles=new HashSet<>();
        if("admin".equals(admin.getType())){
            adminRoles.add("admin");
        }else{
            adminRoles.addAll(roleMapper.getAdminRoleKeys(admin.getId()));
        }
        return adminRoles;
    }

    @Override
    public List<Role> getRoles(Role role, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return roleMapper.getRoles(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getRoles(null);
    }

    @Override
    public void addRole(RoleCreate roleCreate) {
        Role role =new Role();
        BeanUtils.copyProperties(roleCreate, role);
        roleMapper.addRole(role);
    }

    @Override
    public List<RoleMenu> getRoleMenus(Integer id) {
        List<RoleMenu> roleMenus=roleMenuMapper.getRoleMenusByRoleId(id);
        return roleMenus;
    }

    @Override
    public void updateRole(Integer id, RoleUpdate roleUpdate) {
        Role role =new Role();
        BeanUtils.copyProperties(roleUpdate, role);
        roleMapper.updateRole(role);
    }

    @Override
    public void updateRoleMenu(Integer id, List<Integer> menusIds) {
        roleMenuMapper.deleteRoleMenu(id);
        //添加角色菜单权限
        for(Integer menuId: menusIds){
            RoleMenu smsRoleMenu =new RoleMenu();
            smsRoleMenu.setRoleId(id);
            smsRoleMenu.setMenuId(menuId);
            roleMenuMapper.addRoleMenu(smsRoleMenu);
        }
    }

    @Override
    public void deleteRole(Integer id) {
        int count= adminRoleMapper.getAdminRoleCountByRoleId(id);
        if(count>0){
            throw new CommonException("该角色已有管理员关联，请先解除关联！");
        }
        roleMapper.deleteRole(id);
        roleMenuMapper.deleteRoleMenu(id);
    }
}
