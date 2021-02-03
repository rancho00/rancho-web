package com.rancho.web.admin.domain.serve;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NotNull(message = "服务不能为空")
public class ServeCreate {

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "上传目录")
    @NotBlank(message = "上传目录不能为空")
    private String uploadPath;

    @ApiModelProperty(value = "部署路径")
    @NotBlank(message = "部署路径不能为空")
    private String deployPath;

    @ApiModelProperty(value = "备份路径")
    @NotBlank(message = "备份路径不能为空")
    private String backupPath;

    @ApiModelProperty(value = "端口")
    @NotBlank(message = "端口不能为空")
    private String port;

    @ApiModelProperty(value = "启动脚本")
    @NotBlank(message = "启动脚本不能为空")
    private String startScript;

    @ApiModelProperty(value = "部署脚本")
    @NotBlank(message = "部署脚本不能为空")
    private String deployScript;
}
