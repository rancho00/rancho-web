package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.dto.AdminPasswordDto;
import com.rancho.web.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SmsAdminMapper extends BaseMapper<SmsAdmin,Integer> {

    AdminPasswordDto getAdminPasswordDto(@Param("username") String username);

    SmsAdmin getByUsername(@Param("username") String username);

    void saveAdminPasswordDto(AdminPasswordDto adminPasswordDto);
}
