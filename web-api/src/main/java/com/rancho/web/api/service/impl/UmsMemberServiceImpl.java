package com.rancho.web.api.service.impl;

import com.rancho.web.api.service.UmsMemberService;
import com.rancho.web.api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login() {
        String token = jwtTokenUtil.generateToken(110);
        return token;
    }
}
