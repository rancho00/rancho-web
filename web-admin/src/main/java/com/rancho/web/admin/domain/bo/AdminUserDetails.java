package com.rancho.web.admin.domain.bo;

import com.rancho.web.admin.domain.SmsMenu;
import com.rancho.web.admin.domain.dto.AdminPasswordDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SpringSecurity需要的用户详情
 */
public class AdminUserDetails implements UserDetails {
    private AdminPasswordDto adminPasswordDto;
    private List<SmsMenu> smsMenuList;
    public AdminUserDetails(AdminPasswordDto adminPasswordDto, List<SmsMenu> smsMenuList) {
        this.adminPasswordDto = adminPasswordDto;
        this.smsMenuList = smsMenuList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        List<SimpleGrantedAuthority> permission=new ArrayList<>();
        for(SmsMenu dir: smsMenuList){
            permission.add(new SimpleGrantedAuthority(dir.getValue()));
            for(SmsMenu menu:dir.getSmsMenuList()){
                permission.add(new SimpleGrantedAuthority(menu.getValue()));
                for(SmsMenu permissionMenu:menu.getSmsMenuList()){
                    permission.add(new SimpleGrantedAuthority(permissionMenu.getValue()));
                }
            }
        }
        return permission;
    }

    @Override
    public String getPassword() {
        return adminPasswordDto.getPassword();
    }

    @Override
    public String getUsername() {
        return adminPasswordDto.getUsername();
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
        return adminPasswordDto.getStatus().equals(1);
    }
}
