package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.server.ServerConnect;
import com.rancho.web.admin.domain.server.ServerCreate;
import com.rancho.web.admin.domain.server.ServerUpdate;
import com.rancho.web.admin.domain.server.ServerParam;
import com.rancho.web.admin.service.ServerService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "服务器管理", tags = "服务器管理")
@Controller
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @Log("查询服务器")
    @ApiOperation(value = "服务器列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('server:list')")
    public ResponseEntity<CommonResult<PageInfo<Server>>> getServers(ServerParam serverParam, Page page) {
        PageInfo<Server> pageInfo= PageInfo.convertPage(serverService.getServers(serverParam,page));
        return ResponseEntity.ok(CommonResult.ok(pageInfo));
    }


    @Log("添加服务器")
    @ApiOperation(value = "添加服务器")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('server:add')")
    public ResponseEntity<CommonResult> addServer(@Validated @RequestBody ServerCreate serverCreate) {
        serverService.addServer(serverCreate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("更新服务器")
    @ApiOperation(value = "更新服务器")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('server:update')")
    public ResponseEntity<CommonResult> updateServer(@PathVariable Integer id, @Validated @RequestBody ServerUpdate serverUpdate) {
        serverService.updateServer(id, serverUpdate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("删除服务器")
    @ApiOperation(value = "删除服务器")
    @DeleteMapping("/{ids}")
    @ResponseBody
    @PreAuthorize("hasAuthority('server:delete')")
    public ResponseEntity<CommonResult> deleteServer(@PathVariable("ids") Integer[] ids) {
        serverService.deleteServer(ids);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("连接服务器")
    @ApiOperation(value = "连接服务器")
    @PostMapping("/connect")
    @ResponseBody
    @PreAuthorize("hasAuthority('server:connect')")
    public ResponseEntity<CommonResult> connect(@Validated @RequestBody ServerConnect serverConnect) {
        boolean status=serverService.connect(serverConnect);
        return ResponseEntity.ok(CommonResult.ok(status));
    }

}
