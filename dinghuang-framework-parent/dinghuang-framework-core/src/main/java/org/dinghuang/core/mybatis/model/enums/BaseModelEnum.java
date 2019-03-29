package org.dinghuang.core.mybatis.model.enums;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
public enum BaseModelEnum {

    ID("id"),
    CREATED_BY("created_by"),
    CREATED_BY_NAME("created_by_name"),
    CREATION_DATE("creation_date"),
    LAST_UPDATED_BY("last_updated_by"),
    LAST_UPDATED_BY_NAME("last_updated_by_name"),
    LAST_UPDATED_DATE("last_updated_date"),
    OBJECT_VERSION_NUMBER("object_version_number");

    private String value;

    BaseModelEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
