package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.SmsAdminRole;
import com.rancho.web.admin.domain.dto.adminDto.AdminBaseDto;
import com.rancho.web.admin.domain.dto.adminDto.AdminLoginDto;
import com.rancho.web.admin.mapper.SmsAdminMapper;
import com.rancho.web.admin.mapper.SmsAdminRoleMapper;
import com.rancho.web.admin.mapper.SmsMenuMapper;
import com.rancho.web.admin.service.SmsAdminService;
import com.rancho.web.admin.util.JwtTokenUtil;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SmsAdminServiceImpl extends BaseService implements SmsAdminService {

    @Resource
    private SmsAdminMapper adminMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SmsAdminRoleMapper smsAdminRoleMapper;

    @Autowired
    private SmsMenuMapper smsMenuMapper;

    @Override
    public String login(AdminLoginDto adminLoginDto) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginDto.getUsername());
           if(!passwordEncoder.matches(adminLoginDto.getPassword(),userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    @Override
    public List<SmsAdmin> list(SmsAdmin smsAdmin,Page page) {
        setPage(page);
        return adminMapper.list(smsAdmin);
    }

    @Override
    public void save(AdminBaseDto adminBaseDto) {
        SmsAdmin admin=adminMapper.getByUsername(adminBaseDto.getUsername());
        if(admin!=null){
            throw new CommonException("管理员已存在");
        }
        adminBaseDto.setPassword(new BCryptPasswordEncoder().encode(adminBaseDto.getPassword()));
        BeanUtils.copyProperties(adminBaseDto,admin);
        adminMapper.save(admin);
        //添加角色
        for(Integer roleId:adminBaseDto.getRoleIdList()){
            SmsAdminRole smsAdminRole =new SmsAdminRole();
            smsAdminRole.setAdminId(admin.getId());
            smsAdminRole.setRoleId(roleId);
            smsAdminRole.setCreateTime(new Date());
            smsAdminRoleMapper.save(smsAdminRole);
        }
    }

    @Override
    public SmsAdmin getById(Integer id) {
        return adminMapper.getById(id);
    }


    @Override
    public void update(AdminBaseDto adminBaseDto) {
        SmsAdmin smsAdmin=new SmsAdmin();
        BeanUtils.copyProperties(adminBaseDto,smsAdmin);
        adminMapper.update(smsAdmin);
        //先删除角色再添加角色
        smsAdminRoleMapper.deleteByAdminId(adminBaseDto.getId());
        for(Integer roleId:adminBaseDto.getRoleIdList()){
            SmsAdminRole smsAdminRole =new SmsAdminRole();
            smsAdminRole.setAdminId(adminBaseDto.getId());
            smsAdminRole.setRoleId(roleId);
            smsAdminRole.setCreateTime(new Date());
            smsAdminRoleMapper.save(smsAdminRole);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        SmsAdmin smsAdmin =new SmsAdmin();
        smsAdmin.setId(id);
        smsAdmin.setStatus(status);
        adminMapper.update(smsAdmin);
    }

    @Override
    public SmsAdmin getByUsername(String username) {
        return adminMapper.getByUsername(username);
    }

    @Override
    public AdminBaseDto getAdminBaseDtoById(Integer id) {
        SmsAdmin admin=adminMapper.getById(id);
        AdminBaseDto adminBaseDto=new AdminBaseDto();
        BeanUtils.copyProperties(admin,adminBaseDto);
        //查询角色
        SmsAdminRole smsAdminRole =new SmsAdminRole();
        smsAdminRole.setAdminId(id);
        List<SmsAdminRole> smsAdminRoleList = smsAdminRoleMapper.list(smsAdminRole);
        List<Integer> roleIdList=new ArrayList<>();
        for(SmsAdminRole sar: smsAdminRoleList){
            roleIdList.add(sar.getRoleId());
        }
        adminBaseDto.setRoleIdList(roleIdList);
        return adminBaseDto;
    }
}
