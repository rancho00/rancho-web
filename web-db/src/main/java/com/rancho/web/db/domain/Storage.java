package com.rancho.web.db.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Storage {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "真实文件名")
    private String realName;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "后缀")
    private String suffix;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "大小")
    private String size;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
