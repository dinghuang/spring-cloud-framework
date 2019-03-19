package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.dinghuang.core.mybatis.model.enums.BaseModelEnum;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 审计字段放入逻辑
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Configuration
public class CustomMetaObjectConfiguration implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //todo 获取当前登录用户填入
        Date date = new Date();
        this.setInsertFieldValByName("createUser", 16320L, metaObject);
        this.setInsertFieldValByName("createDate", date, metaObject);
        this.setInsertFieldValByName("locked", 0, metaObject);
        this.setInsertFieldValByName("lockUser", 0L, metaObject);
        this.setInsertFieldValByName("lockDate", date, metaObject);
        this.setInsertFieldValByName("lockKey", null, metaObject);
        this.setInsertFieldValByName("updateUser", 16320L, metaObject);
        this.setInsertFieldValByName("updateDate", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("updateUser", 16320L, metaObject);
        this.setInsertFieldValByName("updateDate", new Date(), metaObject);
    }
}
