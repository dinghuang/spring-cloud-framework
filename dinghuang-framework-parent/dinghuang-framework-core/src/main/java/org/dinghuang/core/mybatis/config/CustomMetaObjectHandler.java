package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.dinghuang.core.mybatis.model.enums.BaseModelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        //todo 获取当前登录用户填入
        Date date = new Date();
        this.setInsertFieldValByName(BaseModelEnum.CREATE_USER.value(), 16320L, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.CREATE_DATE.value(), date, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.LOCKED.value(), 0, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.LOCK_USER.value(), 0L, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.LOCK_DATE.value(), date, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.LOCK_KEY.value(), null, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.UPDATE_USER.value(), 16320L, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.UPDATE_DATE.value(), date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName(BaseModelEnum.UPDATE_DATE.value(), 16320L, metaObject);
        this.setInsertFieldValByName(BaseModelEnum.UPDATE_DATE.value(), new Date(), metaObject);


    }
}
