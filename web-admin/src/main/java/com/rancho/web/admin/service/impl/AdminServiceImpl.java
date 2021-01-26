package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.Admin;
import com.rancho.web.admin.domain.AdminRole;
import com.rancho.web.admin.domain.dto.adminDto.AdminCreate;
import com.rancho.web.admin.domain.dto.adminDto.AdminLogin;
import com.rancho.web.admin.domain.dto.adminDto.AdminUpdate;
import com.rancho.web.admin.domain.vo.AdminWithRole;
import com.rancho.web.admin.mapper.AdminMapper;
import com.rancho.web.admin.mapper.AdminRoleMapper;
import com.rancho.web.admin.mapper.MenuMapper;
import com.rancho.web.admin.service.AdminService;
import com.rancho.web.admin.util.FileUtil;
import com.rancho.web.admin.util.JwtTokenUtil;
import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public String login(AdminLogin adminLogin) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
            if(!passwordEncoder.matches(adminLogin.getPassword(),userDetails.getPassword())){
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
    @CacheEvict(value = "adminPermission",key = "#admin.id")
    public void logout(Admin admin) {

    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.getAdminByUsername(username);
    }


    @Override
    public List<Admin> getAdmins(Admin admin, Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return adminMapper.getAdmins(admin);
    }

    @Override
    public AdminWithRole getAdminWithRole(Integer id) {
        AdminWithRole adminWithRole=new AdminWithRole();
        Admin admin=adminMapper.getAdmin(id);
        BeanUtils.copyProperties(admin,adminWithRole);
        List<AdminRole> adminRoles=adminRoleMapper.getAdminRoleByAdminId(id);
        adminWithRole.setAdminRoles(adminRoles);
        return adminWithRole;
    }

    @Override
    public void addAdmin(AdminCreate adminCreate) {
        Admin admin=adminMapper.getAdminByUsername(adminCreate.getUsername());
        if(admin!=null){
            throw new CommonException("管理员已存在");
        }
        adminCreate.setPassword(new BCryptPasswordEncoder().encode("123456"));
        admin=new Admin();
        BeanUtils.copyProperties(adminCreate,admin);
        adminMapper.addAdmin(admin);
        //添加角色
        for(Integer roleId: adminCreate.getRoleIdList()){
            AdminRole adminRole =new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleId);
            adminRole.setCreateTime(new Date());
            adminRoleMapper.addAdminRole(adminRole);
        }
    }

    @Override
    public void updateAdmin(Integer id, AdminUpdate adminUpdate) {
        Admin admin =new Admin();
        BeanUtils.copyProperties(adminUpdate, admin);
        adminMapper.updateAdmin(admin);
        //先删除角色再添加角色
        adminRoleMapper.deleteByAdminId(adminUpdate.getId());
        for(Integer roleId: adminUpdate.getRoleIdList()){
            AdminRole adminRole =new AdminRole();
            adminRole.setAdminId(adminUpdate.getId());
            adminRole.setRoleId(roleId);
            adminRole.setCreateTime(new Date());
            adminRoleMapper.addAdminRole(adminRole);
        }
    }

    @Override
    public void updateAdminStatus(Integer id, Integer status) {
        adminMapper.updateAdminStatus(id,status);
    }


    @Override
    public void download(List<Admin> adminList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        adminList.forEach(smsAdmin -> {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ID",smsAdmin.getId());
            map.put("用户",smsAdmin.getUsername());
            map.put("昵称",smsAdmin.getNickname());
            map.put("状态",smsAdmin.getStatus()==0?"禁用":"启用");
            map.put("昵称",smsAdmin.getCreateTime());
            list.add(map);
        });
        FileUtil.downloadExcel(list,response);
    }
}
