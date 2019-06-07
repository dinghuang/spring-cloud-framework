package org.dinghuang.core.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
public abstract class BaseModel implements Serializable, Cloneable {

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

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("clone " + this.getClass().getName() + " fail");
        }
        return null;
    }

    public void initEmptyObject() {
        this.objectVersionNumber = null;
        this.createdBy = null;
        this.createdByName = null;
        this.creationDate = null;
        this.lastUpdatedBy = null;
        this.lastUpdatedByName = null;
        this.lastUpdatedDate = null;
    }

}
