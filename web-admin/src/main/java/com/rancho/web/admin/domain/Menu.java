package com.rancho.web.admin.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Menu{

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "父级id")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限值")
    private String permission;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    @ApiModelProperty(value = "uri")
    private String uri;

    @ApiModelProperty(value = "状态（0禁用，1启用）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "是否隐藏->0：否，1：是")
    private Integer isHidden;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
