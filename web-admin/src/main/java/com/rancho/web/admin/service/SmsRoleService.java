package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsRoleService {

    PageInfo pageInfo(SmsRole smsRole, Page page);

    List<SmsRole> list(SmsRole smsRole);

    @Transactional
    void save(SmsRole smsRole);

    SmsRole getById(Integer id);

    @Transactional
    void update(SmsRole smsRole);

    void delete(Integer id);

    List<SmsRole> listByAdminId(Integer adminId);
}
