package org.dinghuang.core.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
public abstract class BaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseModel.class);

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected String createdBy;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected String createdByName;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Date creationDate;

    @TableField(fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_NULL)
    protected String lastUpdatedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_NULL)
    protected String lastUpdatedByName;

    @TableField(fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_NULL)
    protected Date lastUpdatedDate;

    @Version
    protected Long objectVersionNumber;

}

