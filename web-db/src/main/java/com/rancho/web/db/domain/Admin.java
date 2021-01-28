package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Admin{
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态（0：停用，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "类型")
    private String type;

}
