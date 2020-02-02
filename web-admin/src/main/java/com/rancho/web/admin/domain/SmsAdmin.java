package com.rancho.web.admin.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SmsAdmin implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "昵称")
    @NotNull
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态（0：停用，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "角色")
    private List<SmsRole> smsRoleList;

}
