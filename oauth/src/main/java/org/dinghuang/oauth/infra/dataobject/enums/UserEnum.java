package org.dinghuang.oauth.infra.dataobject.enums;

/**
 * 用户信息Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
public enum UserEnum {

    /**
     * 主键ID
     */
    ID("id"),

    /**
     * 用户名
     */
    ACCOUNT("account"),

    /**
     * 密码
     */
    PASSWORD("password"),

    /**
     * 用户姓名
     */
    NAME("name"),

    /**
     * 手机号码
     */
    MOBILE_PHONE("mobile_phone"),

    /**
     * 号码
     */
    PHONE("phone"),

    /**
     * 邮箱
     */
    EMAIL("email"),

    /**
     * 组织编码
     */
    ORG_CODE("org_code");

    private String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
