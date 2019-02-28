package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 审计字段逻辑
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        //todo 获取当前登录用户填入
        this.setInsertFieldValByName("createUser", "", metaObject);
        this.setInsertFieldValByName("createDate", "", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("updateUser", "", metaObject);
        this.setInsertFieldValByName("updateDate", "", metaObject);


    }
}
