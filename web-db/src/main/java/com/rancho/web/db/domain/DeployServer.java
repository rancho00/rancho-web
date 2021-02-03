package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeployServer {

    @ApiModelProperty(value = "服务器id")
    private Integer serverId;

    @ApiModelProperty(value = "部署id")
    private Integer deployId;
}
