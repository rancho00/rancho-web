package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.dto.role.RoleCreate;
import com.rancho.web.admin.domain.dto.role.RoleUpdate;
import com.rancho.web.admin.service.RoleService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Role;
import com.rancho.web.db.domain.RoleMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        return ResponseEntity.ok(CommonResult.ok(pageInfo));
    }

    @Log("查询角色")
    @ApiOperation(value = "角色列表")
    @GetMapping("/simpleList")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:list')")
    public ResponseEntity<CommonResult<List<Role>>> getRoles() {
        List<Role> roles=roleService.getAllRoles();
        return ResponseEntity.ok(CommonResult.ok(roles));
    }

    @Log("添加角色")
    @ApiOperation(value = "添加角色")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('role:add')")
    public ResponseEntity<CommonResult> addRole(@Validated @RequestBody RoleCreate roleCreate) {
        roleService.addRole(roleCreate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("查询角色菜单")
    @ApiOperation(value = "获取角色菜单")
    @GetMapping("/{id}/menu")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:detail')")
    public ResponseEntity<CommonResult<RoleMenu>> getRoleMenus(@PathVariable Integer id) {
        List<RoleMenu> roleWithMenu = roleService.getRoleMenus(id);
        return ResponseEntity.ok(CommonResult.ok(roleWithMenu));
    }

    @Log("更新角色")
    @ApiOperation(value = "更新角色")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<CommonResult> updateRole(@PathVariable Integer id, @Validated @RequestBody RoleUpdate roleUpdate) {
        roleService.updateRole(id, roleUpdate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("更新角色菜单")
    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/{id}/menu")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<CommonResult> updateRoleMenu(@PathVariable Integer id, @Validated @RequestBody Integer[] menuIds) {
        roleService.updateRoleMenu(id, Arrays.asList(menuIds));
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("删除角色")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<CommonResult> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(CommonResult.ok());
    }

}
