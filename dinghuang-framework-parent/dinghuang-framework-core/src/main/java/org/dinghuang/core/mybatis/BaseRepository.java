package org.dinghuang.core.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础仓储，可以对此类进行扩展，扩展后可以直接继承调用
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public interface BaseRepository<T> extends BaseMapper<T> {

//    /**
//     * 根据用户id查询列表
//     *
//     * @param userId 用户id
//     * @return int
//     */
//    List<T> queryByUserId(@Param(Constants.ENTITY) Long userId);

    /**
     * 查询所有
     *
     * @return List
     */
    List<T> queryAll();
}
