package com.rancho.web.admin.domain.dto.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NotNull(message = "菜单不能为空")
public class MenuCreate {

    @ApiModelProperty(value = "父级id")
    @Min(value = 0L, message = "父级ID不能为空")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限值")
    @NotBlank(message = "权限值不能为空")
    private String value;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    @Min(value = 0L, message = "无效权限类型")
    @Max(value = 2L, message = "无效权限类型")
    private Integer type;

    @ApiModelProperty(value = "uri")
    @NotBlank(message = "uri不能为空")
    private String uri;

    @ApiModelProperty(value = "状态（0禁用，1启用）")
    private Integer status;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "是否隐藏->0：否，1：是")
    @Min(value = 0L, message = "无效是否隐藏")
    @Max(value = 1L, message = "无效是否隐藏")
    private Integer isHidden;

    @ApiModelProperty(value = "排序")
    @Min(value = 1, message = "排序不能为空")
    private Integer sort;
}
