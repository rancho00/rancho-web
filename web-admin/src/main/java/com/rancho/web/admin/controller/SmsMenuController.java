package com.rancho.web.admin.controller;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.service.SmsMenuService;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/menu")
@Api(value = "菜单管理", tags = "菜单管理")
public class SmsMenuController {

    @Resource
    private SmsMenuService smsMenuService;

    @ApiOperation(value = "菜单列表")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult list() {
        List<SmsMenu> smsMenuList = smsMenuService.list();
        return CommonResult.success(smsMenuList);
    }

}
