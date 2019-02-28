package org.dinghuang.core.security.enums;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public enum UserEnum {

    USERNAME("username"),
    REAL_NAME("realName"),
    PASSWORD("password"),
    DESCRIPTION("description"),
    EMAIL("email"),
    LAST_PASSWORD_RESET("lastPasswordReset"),
    AUTHORITIES("authorities");

    private String value;

    public String value() {
        return value;
    }

    UserEnum(String value) {
        this.value = value;
    }
}
