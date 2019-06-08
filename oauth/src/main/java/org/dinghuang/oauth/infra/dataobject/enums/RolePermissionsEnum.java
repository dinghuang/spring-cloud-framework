package org.dinghuang.oauth.infra.dataobject.enums;

/**
 * 角色权限Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
public enum RolePermissionsEnum {

    /**
     * 主键ID
     */
    ID("id"),

    /**
     * 路径
     */
    URL("url"),

    /**
     * 角色名称
     */
    ROLE_NAME("role_name"),

    /**
     * 角色id
     */
    ROLE_ID("role_id"),

    /**
     * 用户id
     */
    USER_ID("user_id");

    private String value;

    RolePermissionsEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
