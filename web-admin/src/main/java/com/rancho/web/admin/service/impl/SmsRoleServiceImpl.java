package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.SmsRoleMenu;
import com.rancho.web.admin.mapper.SmsAdminRoleMapper;
import com.rancho.web.admin.mapper.SmsRoleMapper;
import com.rancho.web.admin.mapper.SmsRoleMenuMapper;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.admin.service.SmsRoleService;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SmsRoleServiceImpl extends BaseService implements SmsRoleService {

    @Resource
    private SmsRoleMapper smsRoleMapper;

    @Resource
    private SmsAdminRoleMapper smsAdminRoleMapper;

    @Resource
    private SmsRoleMenuMapper smsRoleMenuMapper;

    @Resource
    private SmsMenuService smsMenuService;

    @Override
    public List<SmsRole> listByAdminId(Integer adminId) {
        return smsRoleMapper.listByAdminId(adminId);
    }


    @Override
    public List<SmsRole> list(SmsRole smsRole, Page page) {
        setPage(page);
        return smsRoleMapper.list(smsRole);
    }

    @Override
    public void save(SmsRole smsRole) {
        smsRole.setCreateTime(new Date());
        smsRoleMapper.save(smsRole);
        //添加角色菜单权限
        List<SmsMenu> smsMenuList = smsRole.getSmsMenuList();
        SmsRoleMenu smsRoleMenu =new SmsRoleMenu();
        for(SmsMenu smsMenu : smsMenuList){
            smsRoleMenu.setRoleId(smsRole.getId());
            smsRoleMenu.setMenuId(smsMenu.getId());
            smsRoleMenu.setCreateTime(new Date());
            smsRoleMenuMapper.save(smsRoleMenu);
            for(SmsMenu nextSmsMenu : smsMenu.getSmsMenuList()){
                smsRoleMenu.setRoleId(smsRole.getId());
                smsRoleMenu.setMenuId(nextSmsMenu.getId());
                smsRoleMenu.setCreateTime(new Date());
                smsRoleMenuMapper.save(smsRoleMenu);
                for(SmsMenu permissionMenu: nextSmsMenu.getSmsMenuList()){
                    smsRoleMenu.setRoleId(smsRole.getId());
                    smsRoleMenu.setMenuId(permissionMenu.getId());
                    smsRoleMenu.setCreateTime(new Date());
                    smsRoleMenuMapper.save(smsRoleMenu);
                }
            }
        }
    }

    @Override
    public SmsRole getById(Integer id) {
        SmsRole smsRole = smsRoleMapper.getById(id);
        //加载权限菜单
        smsRole.setSmsMenuList(smsMenuService.listRoleHierarchyMenus(id));
        return smsRole;
    }

    @Override
    public void update(Integer id,SmsRole smsRole) {
        smsRoleMapper.update(smsRole);
        //删除角色菜单权限
        smsRoleMenuMapper.deleteByRoleId(smsRole.getId());
        //添加角色菜单权限啊
        List<SmsMenu> smsMenuList = smsRole.getSmsMenuList();
        SmsRoleMenu smsRoleMenu =new SmsRoleMenu();
        for(SmsMenu smsMenu : smsMenuList){
            smsRoleMenu.setRoleId(smsRole.getId());
            smsRoleMenu.setMenuId(smsMenu.getId());
            smsRoleMenu.setCreateTime(new Date());
            smsRoleMenuMapper.save(smsRoleMenu);
            for(SmsMenu nextSmsMenu : smsMenu.getSmsMenuList()){
                smsRoleMenu.setRoleId(smsRole.getId());
                smsRoleMenu.setMenuId(nextSmsMenu.getId());
                smsRoleMenu.setCreateTime(new Date());
                smsRoleMenuMapper.save(smsRoleMenu);
                for(SmsMenu permissionMenu: nextSmsMenu.getSmsMenuList()){
                    smsRoleMenu.setRoleId(smsRole.getId());
                    smsRoleMenu.setMenuId(permissionMenu.getId());
                    smsRoleMenu.setCreateTime(new Date());
                    smsRoleMenuMapper.save(smsRoleMenu);
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {
        int count= smsAdminRoleMapper.countByRoleId(id);
        if(count>0){
            throw new CommonException("该角色已有管理员关联，请先解除关联！");
        }
        smsRoleMapper.delete(id);
        smsRoleMenuMapper.deleteByRoleId(id);
    }
}
