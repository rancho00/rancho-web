package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.admin.service.DeployService;
import com.rancho.web.admin.service.ServeService;
import com.rancho.web.admin.service.ServerService;
import com.rancho.web.admin.util.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(value = "部署管理", tags = "部署管理")
@Controller
@RequestMapping("/deploy")
public class DeployController {

    @Autowired
    private DeployService deployService;

    @Autowired
    private ServerService serverService;

    @Autowired
    private ServeService serveService;

    @Log("查询部署")
    @ApiOperation(value = "部署列表")
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:list')")
    public ResponseEntity<CommonResult<PageInfo<DeployInfo>>> getDeployInfos(Page page) {
        List<DeployInfo> deploys=deployService.getDeployInfos(page);
        PageInfo<DeployInfo> pageInfo= PageInfo.convertPage(deploys);
        return ResponseEntity.ok(CommonResult.ok(pageInfo));
    }

    @Log("查询服务器")
    @ApiOperation(value = "服务器列表")
    @GetMapping("/server")
    @ResponseBody
    @PreAuthorize("hasAuthority('server:list')")
    public ResponseEntity<CommonResult<Map<String,Object>>> getServerOptions() {
        List<Map<String,Object>> serverOptions=serverService.getServerOptions();
        return ResponseEntity.ok(CommonResult.ok(serverOptions));
    }

    @Log("查询服务")
    @ApiOperation(value = "服务列表")
    @GetMapping("/serve")
    @ResponseBody
    @PreAuthorize("hasAuthority('serve:list')")
    public ResponseEntity<CommonResult<Map<String,Object>>> getServeOptions() {
        List<Map<String,Object>> serveOptions=serveService.getServeOptions();
        return ResponseEntity.ok(CommonResult.ok(serveOptions));
    }

    @Log("添加部署")
    @ApiOperation(value = "添加部署")
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:add')")
    public ResponseEntity<CommonResult> addDeploy(@Validated @RequestBody DeployCreate deployCreate) {
        deployService.addDeploy(deployCreate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("更新部署")
    @ApiOperation(value = "更新部署")
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:update')")
    public ResponseEntity<CommonResult> updateDeploy(@PathVariable Integer id,@Validated @RequestBody DeployUpdate deployUpdate) {
        deployService.updateDeploy(id,deployUpdate);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("删除部署")
    @ApiOperation(value = "删除部署")
    @DeleteMapping("/{ids}")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:delete')")
    public ResponseEntity<CommonResult> deleteDeploy(@PathVariable("ids") Integer[] ids) {
        deployService.deleteDeploy(ids);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("上传文件")
    @ApiOperation(value = "上传文件")
    @PostMapping("/{id}/upload")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:add')")
    public ResponseEntity<CommonResult> uploadFile(@PathVariable Integer id,@RequestBody MultipartFile file) throws IOException {
        if(file != null){
            String fileName = file.getOriginalFilename();
            File deployFile = new File(FileUtil.getTmpDirPath()+"/"+fileName);
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            deployService.deploy(FileUtil.getTmpDirPath()+"/"+fileName ,id);
        }else{
            return ResponseEntity.badRequest().body(CommonResult.badRequest("没有找到相对应的文件"));
        }
        System.out.println("文件上传的原名称为:"+ Objects.requireNonNull(file).getOriginalFilename());
        Map<String,Object> map = new HashMap<>(2);
        map.put("errno",0);
        //map.put("id",fileName);
        return ResponseEntity.ok(CommonResult.ok());
    }
}
