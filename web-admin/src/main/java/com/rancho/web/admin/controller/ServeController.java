package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.serve.ServeCreate;
import com.rancho.web.admin.domain.serve.ServeParam;
import com.rancho.web.admin.domain.serve.ServeUpdate;
import com.rancho.web.admin.service.ServeService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Serve;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "服务管理", tags = "服务管理")
@Controller
@RequestMapping("/serve")
public class ServeController {

    @Autowired
    private ServeService serveService;

    @Log("查询服务")
    @ApiOperation(value = "服务列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('serve:list')")
    public ResponseEntity<CommonResult<PageInfo<Serve>>> getServers(ServeParam serveParam, Page page) {
        PageInfo<Serve> pageInfo= PageInfo.convertPage(serveService.getServes(serveParam,page));
        return ResponseEntity.ok(CommonResult.ok(pageInfo));
    }


    @Log("添加服务")
    @ApiOperation(value = "添加服务")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('serve:add')")
    public ResponseEntity<CommonResult> addServe(@Validated @RequestBody ServeCreate serveCreate) {
        serveService.addServe(serveCreate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("更新服务")
    @ApiOperation(value = "更新服务")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('serve:update')")
    public ResponseEntity<CommonResult> updateServe(@PathVariable Integer id, @Validated @RequestBody ServeUpdate serveUpdate) {
        serveService.updateServe(id, serveUpdate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("删除服务")
    @ApiOperation(value = "删除服务")
    @DeleteMapping("/{ids}")
    @ResponseBody
    @PreAuthorize("hasAuthority('serve:delete')")
    public ResponseEntity<CommonResult> deleteServe(@PathVariable("ids") Integer[] ids) {
        serveService.deleteServe(ids);
        return ResponseEntity.ok(CommonResult.ok());
    }

}
