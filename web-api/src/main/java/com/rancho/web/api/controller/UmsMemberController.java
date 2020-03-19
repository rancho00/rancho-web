package com.rancho.web.api.controller;

import com.rancho.web.api.annotation.CurrentUser;
import com.rancho.web.api.service.UmsMemberService;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Api(value = "会员管理", tags = "会员管理")
@Controller
@RequestMapping("/member")
public class UmsMemberController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(){
        String token= umsMemberService.login();
        if(StringUtils.isEmpty(token)){
            return CommonResult.failed("账号密码不正确");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenPrefix", tokenPrefix);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "test", notes = "test")
    @PostMapping("/test")
    @ResponseBody
    public CommonResult login(@CurrentUser Integer id){
        return CommonResult.success();
    }
}
