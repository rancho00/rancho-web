package com.rancho.web.admin.domain.dto.deploy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class DeployCreate {

    @ApiModelProperty(value = "服务器id")
    @NotEmpty(message = "服务器ID不能为空")
    private Integer[] serverIds;

    @ApiModelProperty(value = "服务id")
    @Min(value = 1,message = "服务ID不能为空")
    private Integer serveId;
}
