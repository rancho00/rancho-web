package com.rancho.web.admin.domain.vo;

import com.rancho.web.admin.validation.NotAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NotNull(message = "管理员不能为空")
public class AdminVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    @NotAdmin(message = "用户名不能为admin")
    @Size(min=4,message="用户名不能少于{min}位")
    private String username;
    @ApiModelProperty(value = "名称")
    private String nickname;
    @ApiModelProperty(value = "状态->0：禁用，1：正常")
    private Integer status;
    @ApiModelProperty(value = "角色list")
    private List<Integer> roleIdList;
}
