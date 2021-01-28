package com.rancho.web.admin.domain.dto.admin;

import com.rancho.web.db.domain.Admin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminPassword extends Admin {

    @ApiModelProperty(value = "密码")
    private String password;
}
