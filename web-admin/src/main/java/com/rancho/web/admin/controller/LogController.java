package com.rancho.web.admin.controller;

import com.rancho.web.admin.service.LogService;
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
}
