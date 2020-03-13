package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.dto.menu.SmsMenuNode;
import com.rancho.web.admin.mapper.SmsMenuMapper;
import com.rancho.web.admin.mapper.SmsRoleMenuMapper;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsMenuServiceImpl extends BaseService implements SmsMenuService {

    @Resource
    private SmsMenuMapper smsMenuMapper;

    @Resource
    private SmsRoleMenuMapper smsRoleMenuMapper;

    @Override
    public List<SmsMenuNode> listTreeMenus() {
        List<SmsMenu> menuList= smsMenuMapper.list(null);
        List<SmsMenuNode> menuNodeList=menuList.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menuList))
                .collect(Collectors.toList());

        return menuNodeList;
    }

    @Override
    public List<SmsMenuNode> listAdminTreeMenus(Integer adminId) {
        //查询目录
        List<SmsMenu> menuList = smsMenuMapper.listAdminMenus(adminId);
        List<SmsMenuNode> menuNodeList=menuList.stream()
                .filter(menu -> menu.getType().equals(0))
                .map(menu -> covert(menu,menuList))
                .collect(Collectors.toList());

        return menuNodeList;
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

    @Override
    public List<SmsMenu> listRoleMenus(Integer roleId) {
        List<SmsMenu> smsMenuList = smsMenuMapper.listRoleMenus(roleId);
        return smsMenuList;
    }

    @Override
    public List<SmsMenu> listAdminMenus(Integer adminId) {
        return smsMenuMapper.listAdminMenus(adminId);
    }


    /**
     *
     * @param menu
     * @param menuList
     * @return
     */
    private SmsMenuNode covert(SmsMenu menu,List<SmsMenu> menuList){
        SmsMenuNode node = new SmsMenuNode();
        BeanUtils.copyProperties(menu,node);
        List<SmsMenu> children = menuList.stream()
                .filter(subMenu -> subMenu.getPid().equals(menu.getId()))
                .map(subMenu -> covert(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
