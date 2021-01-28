package com.rancho.web.admin.domain.vo;

import com.rancho.web.db.domain.Admin;
import com.rancho.web.db.domain.AdminRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdminWithRole extends Admin {

    @ApiModelProperty(value = "角色list")
    private List<AdminRole> adminRoles;
}
