package com.rancho.web.admin.domain.bo;

import com.rancho.web.admin.domain.SmsAdmin;
import com.rancho.web.admin.domain.SmsMenu;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 */
public class AdminUserDetails implements UserDetails {
    private SmsAdmin smsAdmin;
    private List<SmsMenu> smsMenuList;
    public AdminUserDetails(SmsAdmin smsAdmin, List<SmsMenu> smsMenuList) {
        this.smsAdmin = smsAdmin;
        this.smsMenuList = smsMenuList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return  smsMenuList.stream().map(menu ->
                        new SimpleGrantedAuthority(menu.getValue())
                ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return smsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return smsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return smsAdmin.getStatus().equals(1);
    }
}
