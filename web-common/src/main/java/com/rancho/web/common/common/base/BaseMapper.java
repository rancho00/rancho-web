package com.rancho.web.common.common.base;

import java.util.List;

/**
 * 基础dao
 */
public interface BaseMapper<T, PK> {


    /**
     * 获取实体
     * @param id id
     */
    T getById(PK id);


    /**
     * 获取list
     * @param model 实体
     */
    List<T> list(T model);

    /**
     * 新增
     * @param model
     */
    void save(T model);

    /**
     * 新增
     * @param model
     */
    //void insert(T model);


    /**
     * 修改
     * @param model 实体
     */
    void update(T model);

    /**
     * 删除
     * @param id id
     */
    void delete(PK id);

    /**
     * 删除
     * @param ids
     */
    void deleteIds(PK[] ids);

    /**
     * 删除
     * @param id
     */
   // void remove(PK id);
}
