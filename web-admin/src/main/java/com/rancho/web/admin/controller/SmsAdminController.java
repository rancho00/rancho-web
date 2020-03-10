package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.SmsAdminRole;
import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.SmsRole;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import com.rancho.web.admin.domain.dto.adminDto.AdminLoginDto;
import com.rancho.web.admin.domain.dto.adminDto.AdminBaseDto;
import com.rancho.web.admin.service.SmsAdminService;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.admin.service.SmsRoleService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "管理员管理", tags = "管理员管理")
@Controller
@RequestMapping("/admin")
public class SmsAdminController {

    @Resource
    private SmsAdminService smsAdminService;

    @Resource
    private SmsMenuService smsMenuService;

    @Resource
    private SmsRoleService smsRoleService;


    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@Validated @RequestBody AdminLoginDto adminLoginDto){
        String token= smsAdminService.login(adminLoginDto);
        if(StringUtils.isEmpty(token)){
            return CommonResult.failed("账号密码不正确");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenPrefix", tokenPrefix);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    @ResponseBody
    public CommonResult info() {
        AdminUserDetails adminUserDetails = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SmsAdmin smsAdmin = smsAdminService.getByUsername(adminUserDetails.getUsername());
        List<SmsRole> smsRoleList;
        //加载角色
        if("admin".equals(smsAdmin.getUsername())){
            smsRoleList = smsRoleService.list(null,null);
        }else {
            smsRoleList = smsRoleService.listByAdminId(smsAdmin.getId());
        }
        //加载管理员菜单
        List<SmsMenu> smsMenuList;
        if("admin".equals(smsAdmin.getUsername())){
            smsMenuList = smsMenuService.listHierarchy();
        }else{
            smsMenuList = smsMenuService.listAdminHierarchyMenus(smsAdmin.getId());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("admin", smsAdmin);
        data.put("roles", smsRoleList);
        data.put("menus", smsMenuList);
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping("/logout")
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("管理员列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('admin:list')")
    public CommonResult<PageInfo<SmsAdmin>> list(SmsAdmin smsAdmin,Page page) {
        PageInfo<SmsAdmin> pageInfo = PageInfo.convertPage(smsAdminService.list(smsAdmin,page));
        return CommonResult.success(pageInfo);
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('admin:save')")
    public CommonResult save(@Validated @RequestBody AdminBaseDto adminBaseDto) {
        smsAdminService.save(adminBaseDto);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取管理员详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin:detail')")
    public  CommonResult<AdminBaseDto> getById(@PathVariable Integer id) {
        AdminBaseDto adminBaseDto = smsAdminService.getAdminBaseDtoById(id);
        return CommonResult.success(adminBaseDto);
    }

    @ApiOperation(value = "更新管理员")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin:update')")
    public CommonResult update(@PathVariable Integer id, @Validated @RequestBody AdminBaseDto adminBaseDto) {
        if(id==null || !id.equals(adminBaseDto.getId())){
            return CommonResult.failed("无效id");
        }
        smsAdminService.update(adminBaseDto);
        return CommonResult.success();
    }

    @ApiOperation(value = "更新管理员状态")
    @PutMapping("/{id}/status")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin:updateStatus')")
    public CommonResult updateStatus(@PathVariable Integer id,Integer status) {
        smsAdminService.updateStatus(id,status);
        return CommonResult.success();
    }

}
