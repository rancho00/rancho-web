package com.rancho.web.common.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Page {

    public static final int DEFAULT_NUMBER=1;
    public static final int DEFAULT_SIZE=10;
    //开始页
    @ApiModelProperty(value = "页数")
    private int pageNumber = DEFAULT_NUMBER;
    //每页大小
    @ApiModelProperty(value = "页大小")
    private int pageSize = DEFAULT_SIZE;
    //排序名称
    @ApiModelProperty(hidden = true)
    private String orderBy;

    public Page getPage(){
        return this;
    }
}
