package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsMenuMapper extends BaseMapper {

    List<SmsMenu> listRoleMenus(@Param("roleId") Integer roleId, @Param("type") Integer type, @Param("pid") Integer pid);

    List<SmsMenu> listAdminMenus(@Param("adminId") Integer adminId, @Param("type") Integer type, @Param("pid") Integer pid);
}
