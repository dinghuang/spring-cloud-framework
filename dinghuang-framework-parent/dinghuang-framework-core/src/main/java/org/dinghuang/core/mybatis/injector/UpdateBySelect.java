package org.dinghuang.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public class UpdateBySelect extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        //todo 自定义一个差量更新的方法，为null和自维护字段不更新进去
        /* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "update sssss" + tableInfo.getTableName();
        /* mapper 接口方法名一致 */
        String method = "updateBySelect";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
    }

}