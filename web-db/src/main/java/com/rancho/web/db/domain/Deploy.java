package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Deploy {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "服务id")
    private Integer serveId;

    @ApiModelProperty(value = "创建者")
    private String createAdmin;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
