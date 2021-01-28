package com.rancho.web.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.mapper.LogMapper;
import com.rancho.web.admin.service.LogService;
import com.rancho.web.admin.util.StringUtils;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> getLogs(Log log, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return logMapper.getLogs(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLog(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.rancho.web.admin.annotation.Log aopLog = method.getAnnotation(com.rancho.web.admin.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String LOGINPATH = "login";
        if(LOGINPATH.equals(signature.getName())){
            try {
                assert argValues != null;
                username = new JSONObject((Integer)argValues[0]).get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logMapper.addLog(log);
    }

    @Override
    public Log getLog(Integer id) {
        return logMapper.getLog(id);
    }
}
