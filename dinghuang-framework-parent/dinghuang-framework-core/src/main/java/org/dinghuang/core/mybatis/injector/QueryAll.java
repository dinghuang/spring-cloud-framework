package org.dinghuang.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public class QueryAll extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        //todo 自定义一个更新的方法 还没写好，有点问题
        /* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "select * from " + tableInfo.getTableName();
        /* mapper 接口方法名一致 */
        String method = "queryAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//        String sql;
//        SqlMethod sqlMethod = SqlMethod.SELECT_LIST;
//        if (tableInfo.isLogicDelete()) {
//            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
//                    sqlWhereEntityWrapper(tableInfo));
//        } else {
//            sqlMethod = SqlMethod.DELETE;
//            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
//                    sqlWhereEntityWrapper(tableInfo));
//        }
//        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//        return addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
        return addSelectMappedStatement(mapperClass, method, sqlSource, List.class, tableInfo);
    }

}