package com.rancho.web.admin.util;

import cn.hutool.json.JSONObject;
import com.rancho.web.admin.domain.bo.AdminUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static AdminUserDetails getUserDetails() {
        AdminUserDetails adminUserDetails = null;
        try {
            adminUserDetails = (AdminUserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            //throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录状态过期");
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
