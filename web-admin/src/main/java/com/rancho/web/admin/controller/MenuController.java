package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.Menu;
import com.rancho.web.admin.domain.dto.menu.MenuCreate;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.admin.service.MenuService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
@Api(value = "菜单管理", tags = "菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Log("查询菜单")
    @ApiOperation(value = "菜单列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:list')")
    public ResponseEntity<CommonResult<PageInfo<Menu>>> getMenus(Menu menu, Page page) {
        return ResponseEntity.ok(new CommonResult().ok(PageInfo.convertPage(menuService.getMenus(menu,page))));
    }

    @Log("添加菜单")
    @ApiOperation(value = "添加菜单")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:add')")
    public ResponseEntity<CommonResult> addMenu(@Validated @RequestBody MenuCreate menuCreate) {
        menuService.addMenu(menuCreate);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @Log("查询菜单详情")
    @ApiOperation(value = "获取菜单详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:detail')")
    public ResponseEntity<CommonResult<Menu>> getMenu(@PathVariable Integer id) {
        Menu menu = menuService.getMenu(id);
        return ResponseEntity.ok(new CommonResult().ok(menu));
    }

    @Log("更新菜单")
    @ApiOperation(value = "更新菜单")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:update')")
    public ResponseEntity<CommonResult> updateMenu( @PathVariable Integer id,@Validated(Update.class) @RequestBody Menu menu) {
        menuService.updateMenu(id, menu);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @Log("更新菜单状态")
    @ApiOperation(value = "更新菜单状态")
    @PutMapping("/{id}/status")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:updateStatus')")
    public ResponseEntity<CommonResult> updateMenuStatus( @PathVariable Integer id,Integer status) {
        menuService.updateMenuStatus(id,status);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @Log("删除菜单")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:delete')")
    public ResponseEntity<CommonResult> deleteMenu(@PathVariable Integer id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok(new CommonResult().ok());
    }

    @ApiOperation(value = "菜单层次列表")
    @GetMapping("/treeMenuList")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:list')")
    public ResponseEntity<CommonResult<List<MenuNode>>> getTreeMenuList() {
        return ResponseEntity.ok(new CommonResult().ok(menuService.getTreeMenus()));
    }

}
