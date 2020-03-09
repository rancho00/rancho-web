package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/menu")
@Api(value = "菜单管理", tags = "菜单管理")
public class SmsMenuController {

    @Resource
    private SmsMenuService smsMenuService;


    @ApiOperation(value = "菜单列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:list')")
    public  CommonResult<PageInfo<SmsMenu>> list(SmsMenu smsMenu, Page page) {
        return CommonResult.success(PageInfo.convertPage(smsMenuService.list(smsMenu,page)));
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:save')")
    public CommonResult save(@Validated(Insert.class) @RequestBody SmsMenu smsMenu) {
        smsMenuService.save(smsMenu);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取菜单详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:detail')")
    public  CommonResult<SmsMenu> getById(@PathVariable Integer id) {
        SmsMenu smsMenu = smsMenuService.getById(id);
        return CommonResult.success(smsMenu);
    }

    @ApiOperation(value = "更新菜单")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:update')")
    public CommonResult update( @PathVariable Integer id,@Validated(Update.class) @RequestBody SmsMenu smsMenu) {
        if(id==null || !id.equals(smsMenu.getId())){
            return CommonResult.failed("无效id");
        }
        smsMenuService.update(id,smsMenu);
        return CommonResult.success();
    }

    @ApiOperation(value = "更新菜单状态")
    @PutMapping("/{id}/status")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:updateStatus')")
    public CommonResult updateStatus( @PathVariable Integer id,Integer status) {
        smsMenuService.updateStatus(id,status);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:delete')")
    public CommonResult delete(@PathVariable Integer id) {
        smsMenuService.delete(id);
        return CommonResult.success();
    }

    @ApiOperation(value = "菜单层次列表")
    @GetMapping("/hierarchyList")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:list')")
    public  CommonResult<List<SmsMenu>> getHierarchyList() {
        return CommonResult.success(smsMenuService.listHierarchy());
    }

}
