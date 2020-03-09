package com.rancho.web.admin.service.impl;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.SmsAdminRole;
import com.rancho.web.admin.domain.dto.AdminPasswordDto;
import com.rancho.web.admin.domain.vo.AdminLoginVo;
import com.rancho.web.admin.mapper.SmsAdminMapper;
import com.rancho.web.admin.mapper.SmsAdminRoleMapper;
import com.rancho.web.admin.mapper.SmsMenuMapper;
import com.rancho.web.admin.service.SmsAdminService;
import com.rancho.web.admin.util.JwtTokenUtil;
import com.rancho.web.common.base.BaseService;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import com.rancho.web.common.page.PageInfo;
import lombok.extern.slf4j.Slf4j;
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
    public String login(AdminLoginVo adminLoginVo) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginVo.getUsername());
           if(!passwordEncoder.matches(adminLoginVo.getPassword(),userDetails.getPassword())){
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
    public SmsAdmin info(String username) {
        return adminMapper.getByUsername(username);
    }

    @Override
    public AdminPasswordDto getAdminPasswordDto(String username) {
        return adminMapper.getAdminPasswordDto(username);
    }

    @Override
    public List<SmsAdmin> list(Page page) {
        setPage(page);
        return adminMapper.list(null);
    }

    @Override
    public void save(AdminPasswordDto adminPasswordDto, List<Integer> roleIdList) {
        SmsAdmin admin=adminMapper.getByUsername(adminPasswordDto.getUsername());
        if(admin!=null){
            throw new CommonException("管理员已存在");
        }
        adminPasswordDto.setPassword(new BCryptPasswordEncoder().encode("123456"));
        adminMapper.saveAdminPasswordDto(adminPasswordDto);
        //添加角色
        for(Integer roleId:roleIdList){
            SmsAdminRole smsAdminRole =new SmsAdminRole();
            smsAdminRole.setAdminId(adminPasswordDto.getId());
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
    public void update(SmsAdmin smsAdmin, List<Integer> roleIdList) {
        adminMapper.update(smsAdmin);
        //先删除角色再添加角色
        smsAdminRoleMapper.deleteByAdminId(smsAdmin.getId());
        for(Integer roleId:roleIdList){
            SmsAdminRole smsAdminRole =new SmsAdminRole();
            smsAdminRole.setAdminId(smsAdmin.getId());
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
}
