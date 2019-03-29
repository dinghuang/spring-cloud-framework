package org.dinghuang.core.mybatis;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dinghuang.core.mybatis.model.BaseModel;
import org.dinghuang.core.mybatis.model.Pageable;
import org.dinghuang.core.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 基础仓储，可以对此类进行扩展，扩展后可以直接继承调用
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public interface BaseRepository<T extends BaseModel> extends BaseMapper<T> {

    /**
     * 创建或者更新
     *
     * @param t t
     * @return int
     */
    default int insertOrUpdate(T t) {
        Field[] fields = t.getClass().getDeclaredFields();
        Boolean condition = true;
        for (Field field : fields) {
            TableId annotation = field.getAnnotation(TableId.class);
            if (annotation != null) {
                Object object = ReflectionUtils.getFieldValue(t, field.getName());
                if (object != null && t.getObjectVersionNumber() != null) {
                    condition = false;
                    break;
                }
            }
        }
        if (condition) {
            t.setObjectVersionNumber(0L);
            return insert(t);
        } else {
            return updateById(t);
        }
    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return IPage
     */
    default Pageable<T> selectPageCondition(Pageable<T> page, Wrapper<T> queryWrapper) {
        QueryWrapper<T> searchQuery = new QueryWrapper<>();
        if (queryWrapper != null) {
            searchQuery = (QueryWrapper<T>) queryWrapper;
        }
        searchQuery.allEq(page.getCondition());
        return (Pageable<T>) selectPage(page, searchQuery);
    }

    int customerDeleteBatchByIds(Collection<? extends Serializable> idList);

    @Override
    default int deleteBatchIds(Collection<? extends Serializable> idList) {
        if (idList == null || idList.isEmpty()) {
            return 0;
        } else {
            return customerDeleteBatchByIds(idList);
        }

    }


}
