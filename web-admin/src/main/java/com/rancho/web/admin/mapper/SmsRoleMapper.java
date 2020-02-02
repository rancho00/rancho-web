package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsRoleMapper extends BaseMapper<SmsRole,Integer> {

    List<SmsRole> listByAdminId(@Param("adminId") Integer adminId);
}
