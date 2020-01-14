package org.dinghuang.oauth.infra.dataobject.enums;

/**
 * 角色Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
public enum RoleEnum {

    /**
     * 主键ID
     */
    ID("id"),

    /**
     * 用户id
     */
    USER_ID("user_id"),

    /**
     * 角色名称
     */
    NAME("name");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
