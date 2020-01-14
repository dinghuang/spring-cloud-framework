package org.dinghuang.oauth.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 角色DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Data
@TableName("role")
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends BaseModel {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色名称
     */
    private String name;


}
