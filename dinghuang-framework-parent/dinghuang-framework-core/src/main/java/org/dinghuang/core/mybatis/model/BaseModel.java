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

    @TableId(value = "UUID", type = IdType.ID_WORKER)
    protected String uuid;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateUser;

    @TableField(fill = FieldFill.INSERT)
    protected String createUser;

    @TableField(fill = FieldFill.INSERT)
    protected Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateDate;

    protected String locked;

    protected String lockUser;

    protected Date lockDate;

    protected Date lockKey;

    @Version
    protected Integer version;

}
