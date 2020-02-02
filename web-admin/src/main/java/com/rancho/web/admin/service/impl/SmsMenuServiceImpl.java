package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.mapper.SmsMenuMapper;
import com.rancho.web.admin.service.SmsMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsMenuServiceImpl implements SmsMenuService {

    @Resource
    private SmsMenuMapper smsMenuMapper;

    @Override
    public List<SmsMenu> list() {
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
    public List<SmsMenu> listAdminMenus(Integer adminId) {
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
    public List<SmsMenu> listRoleMenus(Integer roleId) {
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
}
