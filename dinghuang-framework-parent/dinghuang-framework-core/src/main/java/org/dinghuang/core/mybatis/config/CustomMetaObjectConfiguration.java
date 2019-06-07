package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.dinghuang.core.model.User;
import org.dinghuang.core.utils.UserUtils;
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
        User user = UserUtils.getUser();
        Date date = new Date();
        this.setInsertFieldValByName("createdBy", user.getAccount(), metaObject);
        this.setInsertFieldValByName("createdByName", user.getName(), metaObject);
        this.setInsertFieldValByName("creationDate", date, metaObject);
        this.setInsertFieldValByName("lastUpdatedBy", user.getAccount(), metaObject);
        this.setInsertFieldValByName("lastUpdatedByName", user.getName(), metaObject);
        this.setInsertFieldValByName("lastUpdatedDate", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = UserUtils.getUser();
        this.setInsertFieldValByName("lastUpdatedBy", user.getAccount(), metaObject);
        this.setInsertFieldValByName("lastUpdatedByName", user.getName(), metaObject);
        this.setInsertFieldValByName("lastUpdatedDate", new Date(), metaObject);
    }
}
