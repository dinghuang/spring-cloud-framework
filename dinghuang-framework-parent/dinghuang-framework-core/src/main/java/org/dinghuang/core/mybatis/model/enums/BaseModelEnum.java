package org.dinghuang.core.mybatis.model.enums;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public enum BaseModelEnum {
    UUID("uuid"),
    UPDATE_USER("update_user"),
    CREATE_USER("create_user"),
    CREATE_DATE("create_date"),
    UPDATE_DATE("update_date"),
    LOCKED("locked"),
    LOCK_USER("lock_user"),
    LOCK_DATE("lock_date"),
    LOCK_KEY("lock_key"),
    VERSION("version");

    private String value;

    BaseModelEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
