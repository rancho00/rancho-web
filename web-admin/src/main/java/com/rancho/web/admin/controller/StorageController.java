package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.dto.menu.MenuNode;
import com.rancho.web.admin.service.StorageService;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.Storage;
import com.rancho.web.db.domain.StorageConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "文件管理", tags = "文件管理")
@Controller
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @Log("查询文件")
    @ApiOperation(value = "查询文件列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('storage:list')")
    public ResponseEntity<CommonResult<PageInfo<Storage>>> getStorages(Page page) {
        List<Storage> storageList = storageService.getStorages(page);
        PageInfo pageInfo = PageInfo.convertPage(storageList);
        return ResponseEntity.ok(CommonResult.ok(pageInfo));
    }

    //@Log("添加文件")
    @ApiOperation(value = "添加文件")
    @PostMapping
    @ResponseBody
    //@PreAuthorize("hasAuthority('storage:add')")
    public ResponseEntity<CommonResult> addStorage(@RequestBody String name) throws IOException {
        Storage storage=new Storage();
        storage.setName(name);
        storageService.addStorage(storage,null);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("查询文件配置")
    @ApiOperation(value = "查询文件配置")
    @GetMapping("/config")
    @ResponseBody
    @PreAuthorize("hasAuthority('storage:update')")
    public ResponseEntity<CommonResult> getStorageConfig() {
        StorageConfig storageConfig = storageService.getStorageConfig();
        return ResponseEntity.ok(CommonResult.ok(storageConfig));
    }

    @Log("修改文件配置")
    @ApiOperation(value = "修改文件配置")
    @PostMapping("/config")
    @ResponseBody
    @PreAuthorize("hasAuthority('storage:update')")
    public ResponseEntity<CommonResult> updateStorageConfig(@Validated @RequestBody StorageConfig storageConfig) {
        storageService.updateStorageConfig(storageConfig);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("删除文件")
    @ApiOperation(value = "删除文件")
    @DeleteMapping("/{ids}")
    @ResponseBody
    @PreAuthorize("hasAuthority('storage:delete')")
    public ResponseEntity<CommonResult> deleteStorage(@PathVariable Integer[] ids) {
        storageService.deleteStorages(ids);
        return ResponseEntity.ok(CommonResult.ok());
    }
}
