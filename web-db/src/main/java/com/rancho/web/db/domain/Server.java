package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Server {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "端口")
    private int port;

    @ApiModelProperty(value = "创建者")
    private String createAdmin;

    @ApiModelProperty(value = "更新者")
    private String updateAdmin;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
