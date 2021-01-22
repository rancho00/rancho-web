package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.Role;
import com.rancho.web.admin.domain.dto.role.RoleBase;
import com.rancho.web.admin.service.RoleService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "角色管理", tags = "角色管理")
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Log("查询角色")
    @ApiOperation(value = "角色列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public ResponseEntity<CommonResult<PageInfo<Role>>> getRoles(Role role, Page page) {
        PageInfo<Role> pageInfo= PageInfo.convertPage(roleService.getRoles(role,page));
        return ResponseEntity.ok(new CommonResult<>().ok(pageInfo));
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/simpleList")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public ResponseEntity<CommonResult<List<Role>>> getRoles(Role role) {
        List<Role> roles=roleService.getRoles(role,null);
        return ResponseEntity.ok(new CommonResult().ok(roles));
    }

    @Log("添加角色")
    @ApiOperation(value = "添加角色")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('role:add')")
    public ResponseEntity<CommonResult> addRole(@Validated @RequestBody RoleBase roleBase) {
        roleService.addRole(roleBase);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @Log("查询角色详情")
    @ApiOperation(value = "获取角色详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:detail')")
    public ResponseEntity<CommonResult<RoleBase>> getRole(@PathVariable Integer id) {
        RoleBase roleBase = roleService.getRoleBaseById(id);
        return ResponseEntity.ok(new CommonResult().ok(roleBase));
    }

    @Log("更新角色")
    @ApiOperation(value = "更新角色")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<CommonResult> updateRole(@PathVariable Integer id, @Validated @RequestBody RoleBase roleBase) {
        roleService.updateRole(id, roleBase);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @Log("删除角色")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<CommonResult> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(new CommonResult().ok());
    }

}
