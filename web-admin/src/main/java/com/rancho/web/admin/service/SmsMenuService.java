package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;

import java.util.List;

public interface SmsMenuService {

    List<SmsMenu> listHierarchy();

    List<SmsMenu> listAdminHierarchyMenus(Integer adminId);

    List<SmsMenu> list(SmsMenu smsMenu, Page page);

    void save(SmsMenu smsMenu);

    void update(Integer id,SmsMenu smsMenu);

    void updateStatus(Integer id,Integer status);

    void delete(Integer id);

    SmsMenu getById(Integer id);

    List<SmsMenu> listRoleMenus(Integer roleId);
}
