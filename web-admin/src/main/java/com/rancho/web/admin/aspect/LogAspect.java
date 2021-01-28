package com.rancho.web.admin.aspect;

import com.rancho.web.admin.service.LogService;
import com.rancho.web.admin.util.RequestHolder;
import com.rancho.web.admin.util.SecurityUtils;
import com.rancho.web.admin.util.StringUtils;
import com.rancho.web.admin.util.ThrowableUtil;
import com.rancho.web.db.domain.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.rancho.web.admin.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        Log log = new Log();
        log.setLogType("INFO");
        log.setTime(System.currentTimeMillis() - currentTime);
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.addLog(SecurityUtils.getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request),joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log();
        log.setLogType("ERROR");
        log.setTime(System.currentTimeMillis() - currentTime);
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.addLog(SecurityUtils.getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }

}
