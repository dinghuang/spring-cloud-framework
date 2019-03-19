package org.dinghuang.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.dinghuang.core.mybatis.model.Lock;

/**
 * 查询锁相关信息
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/8
 */
public class QueryLock extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, ("<script>\n" + "select is_locked,lock_user,lock_date,lock_key from ") + tableInfo.getTableName() + " where 1=1 <if test=\"id != null\">and id=#{id}</if>\n" + "</script>", modelClass);
        return addSelectMappedStatement(mapperClass, "queryLock", sqlSource, Lock.class, tableInfo);
    }

}