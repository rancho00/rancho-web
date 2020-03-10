package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SmsAdminMapper extends BaseMapper<SmsAdmin,Integer> {

    SmsAdmin getByUsername(@Param("username") String username);
}
