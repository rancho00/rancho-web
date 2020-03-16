package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsLog;
import com.rancho.web.admin.domain.dto.roleDto.SmsRoleBase;
import com.rancho.web.admin.service.SmsLogService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "日志管理", tags = "日志管理")
@Controller
@RequestMapping("/log")
public class SmsLogController {

    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "日志列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('log:list')")
    public CommonResult<PageInfo<SmsLog>> list(SmsLog smsLog, Page page) {
        return CommonResult.success(PageInfo.convertPage(smsLogService.list(smsLog,page)));
    }

    @ApiOperation(value = "日志详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('log:detail')")
    public  CommonResult<SmsLog> getById(@PathVariable Integer id) {
        return CommonResult.success(smsLogService.getById(id));
    }
}
