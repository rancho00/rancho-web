package com.rancho.web.admin.controller;

import com.rancho.web.admin.service.LogService;
import com.rancho.web.admin.util.ShellUtil;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Api(value = "日志管理", tags = "日志管理")
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "日志列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('log:list')")
    public ResponseEntity<CommonResult<PageInfo<Log>>> getLogs(Log log, Page page) {
        return ResponseEntity.ok(CommonResult.ok(PageInfo.convertPage(logService.getLogs(log,page))));
    }

    @ApiOperation(value = "日志详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('log:detail')")
    public ResponseEntity<CommonResult<Log>> getLog(@PathVariable Integer id) {
        return ResponseEntity.ok(CommonResult.ok(logService.getLog(id)));
    }

    @ApiOperation(value = "服务器日志列表")
    @GetMapping("/server")
    @ResponseBody
    //@PreAuthorize("hasAuthority('log:list')")
    public ResponseEntity<CommonResult<String>> getServerLogs(Integer num) {
        ShellUtil executeShellUtil=new ShellUtil("192.168.0.103","root","123456",22);
        String str=executeShellUtil.executeForString("tail -n"+num+" /web/soft/tomcat/apache-tomcat-8.5.43-8080/logs/catalina.out");
        //System.out.println(str);
        Map<String,String> res=new HashMap<>();
        res.put("192.168.0.103",str);
        res.put("192.168.0.104",str);
        return ResponseEntity.ok(CommonResult.ok(res));
    }
}
