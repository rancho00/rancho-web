package com.rancho.web.api.controller.v2;

import com.rancho.web.api.annotation.CurrentUser;
import com.rancho.web.api.constant.Constants;
import com.rancho.web.api.service.UmsMemberService;
import com.rancho.web.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Api(value = "会员管理", tags = "member")
@Controller("member-v2")
@RequestMapping(Constants.V2_API_PATH + "/member")
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
    public ResponseEntity<CommonResult> login(){
        String token= umsMemberService.login();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenPrefix", tokenPrefix);
        return ResponseEntity.ok(CommonResult.ok(tokenMap));
    }

    @ApiOperation(value = "test", notes = "test")
    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<CommonResult> login(@CurrentUser Integer id){
        return ResponseEntity.ok(CommonResult.ok());
    }
}
