package org.dinghuang.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public class LockRecord extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, ("<script>\n" + "update ") + tableInfo.getTableName() + " set <choose>  \n" +
                "\t<when test=\"isLocked\">  \n" +
                "\t\tis_locked = '1',\n" +
                "\t</when >  \n" +
                "\t<otherwise>  \n" +
                "\t    is_locked = '0',\n" +
                "\t</otherwise>  \n" +
                "</choose>\n lock_user = #{lockUser}, lock_date = #{lockDate}, lock_key = #{lockKey} where id=#{id} </script>", modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, "lockRecord", sqlSource);
    }

}