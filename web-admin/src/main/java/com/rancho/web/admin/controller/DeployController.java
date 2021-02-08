package com.rancho.web.admin.controller;

import com.rancho.web.admin.annotation.Log;
import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.admin.domain.vo.AdminWithRole;
import com.rancho.web.admin.service.DeployService;
import com.rancho.web.admin.service.ServeService;
import com.rancho.web.admin.service.ServerService;
import com.rancho.web.admin.util.FileUtil;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.db.domain.DeployHistory;
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
import java.util.List;
import java.util.Map;

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

    @Log("查询部署历史")
    @ApiOperation(value = "部署历史列表")
    @GetMapping("/{id}/history")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:list')")
    public ResponseEntity<CommonResult<PageInfo<DeployHistory>>> getDeployHistorys(@PathVariable Integer id, Page page) {
        List<DeployHistory> deployHistories=deployService.getDeployHistoryByDeployId(id,page);
        PageInfo<DeployHistory> pageInfo= PageInfo.convertPage(deployHistories);
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

    @Log("查询部署详情")
    @ApiOperation(value = "查询部署详情")
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:detail')")
    public ResponseEntity<CommonResult<DeployInfo>> getDeployInfo(@PathVariable Integer id) {
        DeployInfo deployInfo = deployService.getDeployInfo(id);
        return ResponseEntity.ok(CommonResult.ok(deployInfo));
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

    @Log("自动部署")
    @ApiOperation(value = "自动部署")
    @PostMapping("/{id}/autoDeploy")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:execute')")
    public ResponseEntity<CommonResult> autoDeploy(@PathVariable Integer id,@RequestBody MultipartFile file) throws IOException, InterruptedException {
        if(file != null){
            String fileName = file.getOriginalFilename();
            File deployFile = new File(FileUtil.getTmpDirPath()+"/"+fileName);
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            deployService.deploy(id,FileUtil.getTmpDirPath()+"/"+fileName );
        }else{
            return ResponseEntity.badRequest().body(CommonResult.badRequest("没有找到相对应的文件"));
        }
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("回退")
    @ApiOperation(value = "回退")
    @PostMapping("/restore")
    @ResponseBody
    @PreAuthorize("hasAuthority('deploy:execute')")
    public ResponseEntity<CommonResult> restore(Integer historyId) throws IOException, InterruptedException {
        deployService.restore(historyId);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("服务运行状态")
    @ApiOperation(value = "服务运行状态")
    @PostMapping(value = "/{id}/serverStatus")
    @PreAuthorize("hasAuthority('deploy:execute')")
    public ResponseEntity<CommonResult> serverStatus(@PathVariable Integer id) throws IOException, InterruptedException{
        deployService.serverStatus(id);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("启动服务")
    @ApiOperation(value = "启动服务")
    @PostMapping(value = "/{id}/startServer")
    @PreAuthorize("hasAuthority('deploy:execute')")
    public ResponseEntity<CommonResult> startServer(@PathVariable Integer id) throws IOException, InterruptedException{
        deployService.startServer(id);
        return ResponseEntity.ok(CommonResult.ok());
    }

    @Log("停止服务")
    @ApiOperation(value = "停止服务")
    @PostMapping(value = "/{id}/stopServer")
    @PreAuthorize("hasAuthority('deploy:execute')")
    public ResponseEntity<CommonResult> stopServer(@PathVariable Integer id) throws IOException, InterruptedException{
        deployService.stopServer(id);
        return ResponseEntity.ok(CommonResult.ok());
    }
}
