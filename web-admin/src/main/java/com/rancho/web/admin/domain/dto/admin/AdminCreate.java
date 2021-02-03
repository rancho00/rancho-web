package com.rancho.web.admin.domain.dto.admin;

import com.rancho.web.admin.validation.NotAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NotNull(message = "管理员不能为空")
public class AdminCreate {

    @ApiModelProperty(value = "用户名")
    @NotAdmin(message = "用户名不能为admin")
    @Size(min=4,message="用户名不能少于{min}位")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空",groups = {Insert.class})
    private String password;

    @ApiModelProperty(value = "名称")
    private String nickname;

    @ApiModelProperty(value = "状态->0：禁用，1：正常")
    private Integer status;

    @ApiModelProperty(value = "类型->admin，common")
    private String type;

    @ApiModelProperty(value = "角色list")
    private List<Integer> roleIds;
}
