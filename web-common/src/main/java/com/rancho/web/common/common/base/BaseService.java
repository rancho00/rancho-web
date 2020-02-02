package com.rancho.web.common.common.base;

import com.github.pagehelper.PageHelper;
import com.rancho.web.common.page.Page;
import org.springframework.util.StringUtils;

/**
 * 基础 service
 */
public abstract class BaseService {
    /**
     * 设置分页信息
     * @param page
     */
    protected void setPage(Page page) {
        if (StringUtils.isEmpty(page.getOrderBy())) {
            PageHelper.startPage(page.getPageNumber(), page.getPageSize(), page.isCount());
        } else {
            PageHelper.startPage(page.getPageNumber(), page.getPageSize(), page.isCount()).setOrderBy(page.getOrderBy());
        }
    }
}
