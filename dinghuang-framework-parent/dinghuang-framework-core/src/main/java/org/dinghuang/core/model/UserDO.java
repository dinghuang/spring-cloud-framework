package org.dinghuang.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dinghuang.core.mybatis.model.BaseModel;

/**
 * 用户信息DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseModel {

    /**
     * 主键ID
     */
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

    private String role;


}
