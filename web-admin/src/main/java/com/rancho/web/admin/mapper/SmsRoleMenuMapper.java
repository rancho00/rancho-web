package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsRoleMenu;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SmsRoleMenuMapper extends BaseMapper<SmsRoleMenu,Integer> {

    void deleteByRoleId(@Param("roleId")Integer roleId);

    int countByMenuId(@Param("menuId")Integer menuId);
}
