package com.rancho.web.admin.domain.dto.menu;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class MenuUpdate extends MenuCreate{

    @Min(value = 1L, message = "ID不能为空")
    private Integer id;


}
