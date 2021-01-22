package com.rancho.web.admin.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Date;

@Data
public class Log implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "操作用户")
    private String username;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "方法名")
    private String method;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "日志类型->INFO,ERROR")
    private String logType;

    @ApiModelProperty(value = "请求ip")
    private String requestIp;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "请求耗时")
    private Long time;

    @ApiModelProperty(value = "异常详细")
    private String exceptionDetail;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

}
