package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StorageConfig {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "服务器id")
    private Integer serverId;

    @ApiModelProperty(value = "路径")
    private String path;
}
