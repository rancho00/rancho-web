package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.mapper.SmsMenuMapper;
import com.rancho.web.admin.mapper.SmsRoleMenuMapper;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsMenuServiceImpl extends BaseService implements SmsMenuService {

    @Resource
    private SmsMenuMapper smsMenuMapper;

    @Resource
    private SmsRoleMenuMapper smsRoleMenuMapper;

    @Override
    public List<SmsMenu> listHierarchy() {
        //查询目录
        SmsMenu smsMenu =new SmsMenu();
        smsMenu.setType(0);
        List<SmsMenu> smsMenuList = smsMenuMapper.list(smsMenu);
        //查询菜单
        for(SmsMenu dir: smsMenuList){
            smsMenu.setType(1);
            smsMenu.setPid(dir.getId());
            List<SmsMenu> menuList= smsMenuMapper.list(smsMenu);
            dir.setSmsMenuList(menuList);
            //查询菜单功能
            for(SmsMenu menu:menuList){
                smsMenu.setType(2);
                smsMenu.setPid(menu.getId());
                List<SmsMenu> permissionMenuList= smsMenuMapper.list(smsMenu);
                menu.setSmsMenuList(permissionMenuList);
            }
        }
        return smsMenuList;
    }

    @Override
    public List<SmsMenu> listAdminHierarchyMenus(Integer adminId) {
        //查询目录
        List<SmsMenu> smsMenuList = smsMenuMapper.listAdminMenus(adminId,0,0);
        //查询菜单
        for(SmsMenu dir: smsMenuList){
            List<SmsMenu> menuList= smsMenuMapper.listAdminMenus(adminId,1,dir.getId());
            dir.setSmsMenuList(menuList);
            //查询菜单功能
            for(SmsMenu menu:menuList){
                List<SmsMenu> permissionMenuList= smsMenuMapper.listAdminMenus(adminId,2,menu.getId());
                menu.setSmsMenuList(permissionMenuList);
            }
        }
        return smsMenuList;
    }

    @Override
    public List<SmsMenu> listRoleHierarchyMenus(Integer roleId) {
        //查询目录
        List<SmsMenu> smsMenuList = smsMenuMapper.listRoleMenus(roleId,0,0);
        //查询菜单
        for(SmsMenu dir: smsMenuList){
            List<SmsMenu> menuList= smsMenuMapper.listRoleMenus(roleId,1,dir.getId());
            dir.setSmsMenuList(menuList);
            //查询菜单功能
            for(SmsMenu menu:menuList){
                List<SmsMenu> permissionMenuList= smsMenuMapper.listRoleMenus(roleId,2,menu.getId());
                menu.setSmsMenuList(permissionMenuList);
            }
        }
        return smsMenuList;
    }

    @Override
    public List<SmsMenu> list(SmsMenu smsMenu, Page page) {
        setPage(page);
        List<SmsMenu> smsMenuList = smsMenuMapper.list(smsMenu);
        return smsMenuList;
    }

    @Override
    public void save(SmsMenu smsMenu) {
        smsMenuMapper.save(smsMenu);
    }

    @Override
    public void update(Integer id,SmsMenu smsMenu) {
        smsMenuMapper.update(smsMenu);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        //查询是否关联角色
        int roleMenuCount=smsRoleMenuMapper.countByMenuId(id);
        if(roleMenuCount>0){
            throw new CommonException("菜单关联了角色，请先解除关联关系！");
        }
        //修改状态
        SmsMenu smsMenu=new SmsMenu();
        smsMenu.setId(id);
        smsMenu.setStatus(status);
        smsMenuMapper.update(smsMenu);
    }

    @Override
    public void delete(Integer id) {
        //查询是否关联角色
        int roleMenuCount=smsRoleMenuMapper.countByMenuId(id);
        if(roleMenuCount>0){
            throw new CommonException("菜单关联了角色，请先解除关联关系！");
        }
        //删除
        smsMenuMapper.delete(id);
    }

    @Override
    public SmsMenu getById(Integer id) {
        return smsMenuMapper.getById(id);
    }
}
