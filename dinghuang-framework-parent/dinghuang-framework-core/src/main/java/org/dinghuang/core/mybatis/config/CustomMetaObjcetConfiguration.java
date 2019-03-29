package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 审计字段放入逻辑
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Configuration
public class CustomMetaObjcetConfiguration implements MetaObjectHandler {

    private static final String userName = "dinghuang";
    private static final String userId = "16320";

    @Override
    public void insertFill(MetaObject metaObject) {
        //todo 获取当前登录用户填入
        Date date = new Date();
        this.setInsertFieldValByName("createdBy", userId, metaObject);
        this.setInsertFieldValByName("createdByName", userName, metaObject);
        this.setInsertFieldValByName("creationDate", date, metaObject);
        this.setInsertFieldValByName("lastUpdatedBy", userId, metaObject);
        this.setInsertFieldValByName("lastUpdatedByName", userName, metaObject);
        this.setInsertFieldValByName("lastUpdatedDate", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("lastUpdatedBy", userId, metaObject);
        this.setInsertFieldValByName("lastUpdatedByName", userName, metaObject);
        this.setInsertFieldValByName("lastUpdatedDate", new Date(), metaObject);
    }
}
