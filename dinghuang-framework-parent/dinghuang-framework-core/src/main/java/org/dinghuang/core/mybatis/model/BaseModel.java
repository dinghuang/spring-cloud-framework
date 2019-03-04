package org.dinghuang.core.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
public abstract class BaseModel {

    @TableId(type = IdType.ID_WORKER)
    protected Long uuid;

    @TableField(fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_NULL)
    protected Long updateUser;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Long createUser;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_NULL)
    protected Date updateDate;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Integer locked;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Long lockUser;

    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.NOT_NULL)
    protected Date lockDate;

    @TableField(fill = FieldFill.INSERT)
    protected String lockKey;

    @Version
    protected Integer version;

}
