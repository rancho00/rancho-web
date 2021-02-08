package com.rancho.web.admin.controller;

import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "监控管理", tags = "监控管理")
@Controller
@RequestMapping("/monitor")
public class MonitorController {
//
//    @ApiOperation(value = "监控列表")
//    @GetMapping
//    @ResponseBody
//    @PreAuthorize("hasAuthority('monitor:list')")
//    public ResponseEntity<CommonResult<PageInfo<Log>>> monitor() {
//        return ResponseEntity.ok(CommonResult.ok(PageInfo.convertPage(logService.getLogs(log,page))));
//    }
}
