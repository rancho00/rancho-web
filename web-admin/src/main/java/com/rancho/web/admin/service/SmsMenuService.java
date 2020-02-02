package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsMenu;

import java.util.List;

public interface SmsMenuService {

    List<SmsMenu> list();

    List<SmsMenu> listAdminMenus(Integer adminId);

    List<SmsMenu> listRoleMenus(Integer roleId);
}
