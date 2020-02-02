package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.service.SmsRoleService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "角色管理", tags = "角色管理")
@Controller
@RequestMapping("/role")
public class SmsRoleController {

    @Resource
    private SmsRoleService smsRoleService;

    @ApiOperation(value = "角色列表")
    @GetMapping("/pageInfo")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public  CommonResult<PageInfo<SmsRole>> pageInfo(SmsRole smsRole, Page page) {
        PageInfo<SmsRole> pageInfo= smsRoleService.pageInfo(smsRole,page);
        return CommonResult.success(pageInfo);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public  CommonResult<List<SmsRole>> list() {
        SmsRole smsRole =new SmsRole();
        smsRole.setStatus(1);
        List<SmsRole> list= smsRoleService.list(smsRole);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/save")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:save')")
    public CommonResult save(@Validated @RequestBody SmsRole smsRole) {
        smsRoleService.save(smsRole);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取角色详情")
    @GetMapping("{id}/getById")
    @ResponseBody
    public  CommonResult<SmsRole> getById(@PathVariable Integer id) {
        SmsRole smsRole = smsRoleService.getById(id);
        return CommonResult.success(smsRole);
    }

    @ApiOperation(value = "更新角色")
    @PutMapping("{id}/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:update')")
    public CommonResult update(@Validated @RequestBody SmsRole smsRole, @PathVariable String id) {
        smsRoleService.update(smsRole);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("{id}/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:delete')")
    public CommonResult delete(@PathVariable Integer id) {
        smsRoleService.delete(id);
        return CommonResult.success();
    }

}
