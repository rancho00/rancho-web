package com.rancho.web.admin.domain.server;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NotNull(message = "服务器不能为空")
public class ServerCreate {

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "ip")
    @NotBlank(message = "ip不能为空")
    private String ip;

    @ApiModelProperty(value = "port")
    @Min(value = 1,message = "port不能为空")
    private Integer port;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
