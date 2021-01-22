package com.rancho.web.common.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页数据封装类
 */
@Getter
@Setter
public class PageInfo<T> {
    @ApiModelProperty(value = "页号码")
    private Integer pageNumber;
    @ApiModelProperty(value = "页大小")
    private Integer pageSize;
    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
    @ApiModelProperty(value = "总条数")
    private Long total;
    @ApiModelProperty(value = "list")
    private List<T> list;

    /**
     * list封装成pageInfo对象
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> convertPage(List<T> list) {
        PageInfo<T> result = new PageInfo<T>();
        com.github.pagehelper.PageInfo<T> pageInfo = new com.github.pagehelper.PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNumber(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

}
