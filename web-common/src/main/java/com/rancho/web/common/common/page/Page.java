package com.rancho.web.common.common.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

    public static final int DEFAULT_NUMBER=1;
    public static final int DEFAULT_SIZE=10;
    //开始页
    private int pageNumber = DEFAULT_NUMBER;
    //每页大小
    private int pageSize = DEFAULT_SIZE;
    //排序名称
    private String orderBy;
    //是否允许统计count(0)-true：允许，false：不允许
    private boolean count=true;

    public Page getPage(){
        return this;
    }
}
