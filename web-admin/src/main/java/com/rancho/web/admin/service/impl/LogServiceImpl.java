package com.rancho.web.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rancho.web.admin.domain.SmsLog;
import com.rancho.web.admin.mapper.SmsLogMapper;
import com.rancho.web.admin.service.SmsLogService;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.page.Page;
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
public class LogServiceImpl extends BaseService implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Override
    public List<SmsLog> list(SmsLog smsLog, Page page) {
        setPage(page);
        return smsLogMapper.list(smsLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SmsLog smsLog){

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
        if (smsLog != null) {
            smsLog.setDescription(aopLog.value());
        }
        assert smsLog != null;
        smsLog.setRequestIp(ip);

        String LOGINPATH = "login";
        if(LOGINPATH.equals(signature.getName())){
            try {
                assert argValues != null;
                username = new JSONObject((Integer) argValues[0]).get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //smsLog.setAddress(StringUtils.getCityInfo(smsLog.getRequestIp()));
        smsLog.setMethod(methodName);
        smsLog.setUsername(username);
        smsLog.setParams(params.toString() + " }");
        smsLog.setBrowser(browser);
        smsLogMapper.save(smsLog);
    }
}
