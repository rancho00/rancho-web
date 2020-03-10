package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.dto.roleDto.RoleBaseDto;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsRoleService {

    List<SmsRole> list(SmsRole smsRole, Page page);

    @Transactional
    void save(RoleBaseDto roleBaseDto);

    @Transactional
    void update(Integer id,RoleBaseDto roleBaseDto);

    void delete(Integer id);

    List<SmsRole> listByAdminId(Integer adminId);

    RoleBaseDto getRoleBaseDtoById(Integer id);
}
