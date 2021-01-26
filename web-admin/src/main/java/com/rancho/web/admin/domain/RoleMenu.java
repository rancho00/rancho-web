package com.rancho.web.admin.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleMenu{
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private Integer menuId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
