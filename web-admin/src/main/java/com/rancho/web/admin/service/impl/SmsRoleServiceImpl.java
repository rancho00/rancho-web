package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.SmsRoleMenu;
import com.rancho.web.admin.domain.dto.roleDto.SmsRoleBase;
import com.rancho.web.admin.mapper.SmsAdminRoleMapper;
import com.rancho.web.admin.mapper.SmsRoleMapper;
import com.rancho.web.admin.mapper.SmsRoleMenuMapper;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.admin.service.SmsRoleService;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    public void save(SmsRoleBase smsRoleBase) {
        SmsRole smsRole=new SmsRole();
        BeanUtils.copyProperties(smsRoleBase,smsRole);
        smsRoleMapper.save(smsRole);
        //添加角色菜单权限
        for(Integer menuId: smsRoleBase.getMenuIdList()){
            SmsRoleMenu smsRoleMenu =new SmsRoleMenu();
            smsRoleMenu.setRoleId(smsRole.getId());
            smsRoleMenu.setMenuId(menuId);
            smsRoleMenuMapper.save(smsRoleMenu);
        }
    }

    @Override
    public SmsRoleBase getRoleBaseDtoById(Integer id) {
        SmsRole smsRole = smsRoleMapper.getById(id);
        SmsRoleBase smsRoleBase =new SmsRoleBase();
        BeanUtils.copyProperties(smsRole, smsRoleBase);
        //加载权限菜单
        smsRoleBase.setMenuIdList(smsMenuService.listRoleMenus(id).stream().map(SmsMenu::getId).collect(Collectors.toList()));
        return smsRoleBase;
    }

    @Override
    public void update(Integer id, SmsRoleBase smsRoleBase) {
        SmsRole smsRole=new SmsRole();
        BeanUtils.copyProperties(smsRoleBase,smsRole);
        smsRoleMapper.update(smsRole);
        //删除角色菜单权限
        smsRoleMenuMapper.deleteByRoleId(smsRole.getId());
        //添加角色菜单权限
        for(Integer menuId: smsRoleBase.getMenuIdList()){
            SmsRoleMenu smsRoleMenu =new SmsRoleMenu();
            smsRoleMenu.setRoleId(smsRole.getId());
            smsRoleMenu.setMenuId(menuId);
            smsRoleMenuMapper.save(smsRoleMenu);
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
