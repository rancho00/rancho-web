package com.rancho.web.admin.util;

import com.rancho.web.admin.domain.bo.AdminUserDetails;
import org.springframework.security.access.AccessDeniedException;

public class SecurityUtils {

    public static AdminUserDetails getUserDetails() {
        AdminUserDetails adminUserDetails;
        try {
            adminUserDetails = (AdminUserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AccessDeniedException("不允许访问");
        }
        return adminUserDetails;
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getUsername(){
        AdminUserDetails adminUserDetails = getUserDetails();
        return adminUserDetails.getUsername();
    }

}
