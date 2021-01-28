package com.rancho.web.admin.service;

import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface LogService {

    List<Log> getLogs(Log log, Page page);

    @Async
    void addLog(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);

    Log getLog(Integer id);
}
