package com.rancho.web.admin.service;



import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.dto.AdminPasswordDto;
import com.rancho.web.admin.domain.vo.AdminLoginVo;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;

import java.util.List;

public interface SmsAdminService {

    String login(AdminLoginVo adminLoginVo);

    SmsAdmin info(String username);

    AdminPasswordDto getAdminPasswordDto(String username);

    PageInfo<SmsAdmin> pageInfo(Page page);

    void save(AdminPasswordDto adminPasswordDto, List<Integer> roleIdList);

    SmsAdmin getById(Integer id);

    void update(SmsAdmin smsAdmin, List<Integer> roleIdList);

    void updateStatus(Integer id,Integer status);
}
