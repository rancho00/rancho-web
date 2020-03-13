package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.dto.roleDto.SmsRoleBase;
import com.rancho.web.admin.service.SmsRoleService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "角色管理", tags = "角色管理")
@Controller
@RequestMapping("/role")
public class SmsRoleController {

    @Autowired
    private SmsRoleService smsRoleService;

    @ApiOperation(value = "角色列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public  CommonResult<PageInfo<SmsRole>> list(SmsRole smsRole, Page page) {
        PageInfo<SmsRole> pageInfo= PageInfo.convertPage(smsRoleService.list(smsRole,page));
        return CommonResult.success(pageInfo);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/simpleList")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public  CommonResult<List<SmsRole>> getRoles(SmsRole smsRole) {
        return CommonResult.success(smsRoleService.list(smsRole,null));
    }

    @ApiOperation(value = "添加角色")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('role:save')")
    public CommonResult save(@Validated @RequestBody SmsRoleBase smsRoleBase) {
        smsRoleService.save(smsRoleBase);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取角色详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:detail')")
    public  CommonResult<SmsRoleBase> getById(@PathVariable Integer id) {
        SmsRoleBase smsRoleBase = smsRoleService.getRoleBaseDtoById(id);
        return CommonResult.success(smsRoleBase);
    }

    @ApiOperation(value = "更新角色")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:update')")
    public CommonResult update(@PathVariable Integer id, @Validated @RequestBody SmsRoleBase smsRoleBase) {
        if(id==null || !id.equals(smsRoleBase.getId())){
            return CommonResult.failed("无效id");
        }
        smsRoleService.update(id, smsRoleBase);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:delete')")
    public CommonResult delete(@PathVariable Integer id) {
        smsRoleService.delete(id);
        return CommonResult.success();
    }

}
