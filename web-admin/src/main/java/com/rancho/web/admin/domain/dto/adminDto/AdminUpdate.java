package com.rancho.web.admin.domain.dto.adminDto;

import com.rancho.web.admin.validation.NotAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NotNull(message = "管理员不能为空")
public class AdminUpdate extends AdminCreate{
    @ApiModelProperty(value = "id")
    @Min(value = 1L,message = "id不能为空")
    private Integer id;
}
