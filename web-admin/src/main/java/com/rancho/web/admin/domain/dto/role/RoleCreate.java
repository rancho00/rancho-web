package com.rancho.web.admin.domain.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NotNull(message = "角色不能为空")
public class RoleCreate {

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态（0禁用，1启用）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
