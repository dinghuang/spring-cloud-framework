package org.dinghuang.security.enums;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public enum AuthoritiesEnum {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    SUPERUSER("ROLE_SUPERUSER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private String value;

    public String value() {
        return value;
    }

    AuthoritiesEnum(String value) {
        this.value = value;
    }
}
