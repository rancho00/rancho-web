package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.dto.menu.SmsMenuNode;
import com.rancho.web.common.page.Page;

import java.util.List;

public interface SmsMenuService {

    List<SmsMenuNode> listTreeMenus();

    List<SmsMenuNode> listAdminTreeMenus(Integer adminId);

    List<SmsMenu> listRoleMenus(Integer roleId);

    List<SmsMenu> listAdminMenus(Integer adminId);


    List<SmsMenu> list(SmsMenu smsMenu, Page page);

    void save(SmsMenu smsMenu);

    void update(Integer id,SmsMenu smsMenu);

    void updateStatus(Integer id,Integer status);

    void delete(Integer id);

    SmsMenu getById(Integer id);
}
