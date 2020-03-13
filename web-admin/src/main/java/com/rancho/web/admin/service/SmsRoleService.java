package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.dto.roleDto.SmsRoleBase;
import com.rancho.web.common.page.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsRoleService {

    List<SmsRole> list(SmsRole smsRole, Page page);

    @Transactional
    void save(SmsRoleBase smsRoleBase);

    @Transactional
    void update(Integer id, SmsRoleBase smsRoleBase);

    void delete(Integer id);

    List<SmsRole> listByAdminId(Integer adminId);

    SmsRoleBase getRoleBaseById(Integer id);
}
