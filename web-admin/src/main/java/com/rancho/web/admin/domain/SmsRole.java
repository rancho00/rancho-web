package com.rancho.web.admin.domain;

import com.rancho.web.admin.validation.NotAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NotNull(message = "角色不能为空")
public class SmsRole implements Serializable {

    @Min(value = 1L,message = "id不能为空", groups = {Update.class})
    private Integer id;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @NotAdmin(message = "不能操作admin", groups = {Insert.class, Update.class})
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态（0禁用，1启用）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<SmsMenu> smsMenuList;

}
