package org.dinghuang.oauth.infra.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dinghuang.core.mybatis.model.BaseModel;

import java.util.List;

/**
 * 用户信息DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@TableName("user_account")
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseModel {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 组织编码
     */
    private String orgCode;

    @TableField(exist = false)
    private String role;


}
