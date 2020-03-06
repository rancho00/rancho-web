package com.rancho.web.file.controller;

import com.rancho.web.common.result.CommonResult;
import com.rancho.web.file.domain.FileVo;
import com.rancho.web.file.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件controller
 */
@Controller
@RequestMapping("")
@Api(value = "文件管理", tags = "文件管理")
public class FileController {

    @Resource
    private FileUtil fileUtil;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "file", paramType="form", required = true),
            @ApiImplicitParam(name = "folderName", value = "文件夹名称", dataType = "String", paramType = "query", required = true)
    })
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<FileVo> upload(@RequestParam("file") MultipartFile multipartFile, String folderName) {
        CommonResult<FileVo> commonResult= fileUtil.upload(multipartFile,folderName);
        return commonResult;
    }

}
