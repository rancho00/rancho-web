package com.rancho.web.admin.domain.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull(message = "管理员不能为空")
public class AdminUpdate extends AdminCreate{
    @ApiModelProperty(value = "id")
    @Min(value = 1L,message = "id不能为空")
    private Integer id;
}
