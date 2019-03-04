package org.dinghuang.core.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public interface BaseRepository<T> extends BaseMapper<T> {

    /**
     * 只有有值的才会更新进去
     *
     * @param entity 实体对象
     * @return int
     */
    int updateBySelect(@Param(Constants.ENTITY) T entity);
}
