package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DeployHistory {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "服务名称")
    private String serveName;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "部署id")
    private Integer deployId;

    @ApiModelProperty(value = "创建者")
    private String createAdmin;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
