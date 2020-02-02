package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsAdminRole;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SmsAdminRoleMapper extends BaseMapper<SmsAdminRole,Integer> {
    int countByRoleId(@Param("roleId") Integer roleId);

    void deleteByAdminId(@Param("adminId") Integer adminId);
}
