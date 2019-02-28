package org.dinghuang.core.service;

import org.dinghuang.core.model.ModelValidator;
import org.dinghuang.core.mybatis.model.BaseModel;

import java.util.Set;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
public interface BaseService<T extends BaseModel> {

    /**
     * 数据校验
     */
    default void validate(T t) {
        //todo 默认数据校验 javax
        ModelValidator.validate(t);
    }

    /**
     * 保存
     */
    default T create(T t) {
        //todo 保存
        return t;
    }

    /**
     * 删除
     */
    default void deleteById(String id) {
        //todo 删除
    }

    /**
     * 批量删除
     */
    default void batchDelete(Set<String> ids) {
        //todo 批量删除
    }

    /**
     * 批量创建
     */
    default void batchCreate(Set<T> list) {
        //todo 批量创建
    }

    /**
     * 查询单个
     */
    default T queryById(String id) {
        //todo 查询单个
        return null;
    }

}
