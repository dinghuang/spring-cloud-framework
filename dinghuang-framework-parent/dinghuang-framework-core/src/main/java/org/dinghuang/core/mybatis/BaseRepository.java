package org.dinghuang.core.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.dinghuang.core.mybatis.model.BaseModel;
import org.dinghuang.core.mybatis.model.Lock;

import java.util.Date;
import java.util.List;

/**
 * 基础仓储，可以对此类进行扩展，扩展后可以直接继承调用
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public interface BaseRepository<T extends BaseModel> extends BaseMapper<T> {

    Lock queryLock(@Param("id") String id);

    int lockRecord(@Param("id") String id, @Param("isLocked") boolean isLocked, @Param("lockUser") String lockUser, @Param("lockDate") Date lockDate, @Param("lockKey") String lockKey);

    int unlock(@Param("id") String id, @Param("lockKey") String lockKey);

    default int insertOrUpdate(T t) {
        if (t.getUuid() != null && t.getVersion() != null) {
            return this.updateById(t);
        } else {
            t.setVersion(0);
            return this.insert(t);
        }
    }
}
