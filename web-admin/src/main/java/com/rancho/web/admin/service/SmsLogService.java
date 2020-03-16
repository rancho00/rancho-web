package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.SmsLog;
import com.rancho.web.common.page.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface SmsLogService {

    List<SmsLog> list(SmsLog smsLog, Page page);

    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SmsLog smsLog);

    SmsLog getById(Integer id);
}
