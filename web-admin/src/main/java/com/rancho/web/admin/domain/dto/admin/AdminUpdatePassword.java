package com.rancho.web.admin.domain.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull(message = "实体不能为空")
public class AdminUpdatePassword{

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空",groups = {Update.class})
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "密码不能为空")
    private String newPassword;

}
