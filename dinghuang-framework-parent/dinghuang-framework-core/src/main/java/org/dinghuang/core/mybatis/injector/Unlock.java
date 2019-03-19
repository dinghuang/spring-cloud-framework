package org.dinghuang.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/8
 */
public class Unlock extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, ("<script>\n" + "update ") + tableInfo.getTableName() + " set is_locked = '0' where id=#{id} and lock_key = #{lockKey} </script>", modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, "unlock", sqlSource);
    }
}