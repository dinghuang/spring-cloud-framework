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

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateUser;

    @TableField(fill = FieldFill.INSERT)
    protected Long createUser;

    @TableField(fill = FieldFill.INSERT)
    protected Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateDate;

    @TableField(fill = FieldFill.INSERT)
    protected Integer locked;

    @TableField(fill = FieldFill.INSERT)
    protected Long lockUser;

    @TableField(fill = FieldFill.INSERT)
    protected Date lockDate;

    @TableField(fill = FieldFill.INSERT)
    protected String lockKey;

    @Version
    protected Integer version;

}
