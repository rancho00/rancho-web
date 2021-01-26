package com.rancho.web.admin.domain.dto.role;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RoleUpdate extends RoleCreate{

    @ApiModelProperty(value = "id")
    @Min(value = 1L,message = "id不能为空")
    private Integer id;
}
