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
@NotNull(message = "菜单不能为空")
public class SmsMenu implements Serializable {
    @ApiModelProperty(value = "id")
    @Min(value = 1L, message = "ID不能为空", groups = {Update.class})
    private Integer id;

    @ApiModelProperty(value = "父级id")
    @Min(value = 0L, message = "父级ID不能为空", groups = {Insert.class, Update.class})
    private Integer pid;

    @ApiModelProperty(value = "名称")
    @NotBlank(groups = {Insert.class, Update.class},message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限值")
    @NotBlank(groups = {Insert.class, Update.class},message = "权限值不能为空")
    private String value;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    @Min(value = 0L, message = "无效权限类型", groups = {Insert.class, Update.class})
    @Max(value = 2L, message = "无效权限类型", groups = {Insert.class, Update.class})
    private Integer type;

    @ApiModelProperty(value = "uri")
    @NotBlank(groups = {Insert.class, Update.class},message = "uri不能为空")
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
    @Min(value = 0L, message = "无效是否隐藏", groups = {Insert.class, Update.class})
    @Max(value = 1L, message = "无效是否隐藏", groups = {Insert.class, Update.class})
    private Integer isHidden;

    @ApiModelProperty(value = "排序")
    @Min(value = 1, message = "排序不能为空",groups = {Insert.class, Update.class})
    private Integer sort;
}
