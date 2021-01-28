package com.rancho.web.admin.domain.bo;

import com.rancho.web.admin.domain.dto.admin.AdminPassword;
import com.rancho.web.db.domain.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 */
public class AdminUserDetails implements UserDetails {
    private AdminPassword adminPassword;
    private Set<String> permissions;
    public AdminUserDetails(AdminPassword adminPassword, Set<String> permissions) {
        this.adminPassword = adminPassword;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return  permissions.stream().map(permission ->
                        new SimpleGrantedAuthority(permission)
                ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return adminPassword.getPassword();
    }

    @Override
    public String getUsername() {
        return adminPassword.getUsername();
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
        return adminPassword.getStatus().equals(1);
    }
}
