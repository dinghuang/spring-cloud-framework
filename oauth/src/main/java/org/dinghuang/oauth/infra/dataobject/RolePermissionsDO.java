package org.dinghuang.oauth.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 角色权限DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Data
@TableName("role_permissions")
@EqualsAndHashCode(callSuper = true)
public class RolePermissionsDO extends BaseModel {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 路径
     */
    private String url;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;


}
