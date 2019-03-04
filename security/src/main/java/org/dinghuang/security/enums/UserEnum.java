package org.dinghuang.security.enums;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public enum UserEnum {
    UUID("uuid"),
    USERNAME("username"),
    CREATE_USER("createUser"),
    CREATE_DATE("createDate"),
    UPDATE_DATE("updateDate"),
    LOCKED("locked"),
    LOCK_USER("lockUser"),
    LOCK_DATE("lockDate"),
    LOCK_KEY("lockKey"),
    VERSION("version");

    private String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
