package org.dinghuang.oauth.infra.dataobject.enums;

/**
 * 认证客户端Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
public enum OauthClientEnum {

    /**
     * 
     */
    ID("id"),

    /**
     * 客户端名称
     */
    NAME("name"),

    /**
     * 组织ID
     */
    ORGANIZATION_ID("organization_id"),

    /**
     * 资源ID列表，目前只使用default
     */
    RESOURCE_IDS("resource_ids"),

    /**
     * 客户端秘钥
     */
    SECRET("secret"),

    /**
     * Oauth授权范围
     */
    SCOPE("scope"),

    /**
     * 支持的授权类型列表
     */
    AUTHORIZED_GRANT_TYPES("authorized_grant_types"),

    /**
     * 授权重定向URL
     */
    WEB_SERVER_REDIRECT_URI("web_server_redirect_uri"),

    /**
     * 客户端特定的AccessToken超时时间
     */
    ACCESS_TOKEN_VALIDITY("access_token_validity"),

    /**
     * 客户端特定的RefreshToken超时时间
     */
    REFRESH_TOKEN_VALIDITY("refresh_token_validity"),

    /**
     * 客户端附加信息
     */
    ADDITIONAL_INFORMATION("additional_information"),

    /**
     * 自动授权范围列表
     */
    AUTO_APPROVE("auto_approve"),

    /**
     * 
     */
    LAST_UPDATE_DATE("last_update_date"),

    /**
     * 
     */
    ID("id"),

    /**
     * 客户端名称
     */
    NAME("name"),

    /**
     * 组织ID
     */
    ORGANIZATION_ID("organization_id"),

    /**
     * 资源ID列表，目前只使用default
     */
    RESOURCE_IDS("resource_ids"),

    /**
     * 客户端秘钥
     */
    SECRET("secret"),

    /**
     * Oauth授权范围
     */
    SCOPE("scope"),

    /**
     * 支持的授权类型列表
     */
    AUTHORIZED_GRANT_TYPES("authorized_grant_types"),

    /**
     * 授权重定向URL
     */
    WEB_SERVER_REDIRECT_URI("web_server_redirect_uri"),

    /**
     * 客户端特定的AccessToken超时时间
     */
    ACCESS_TOKEN_VALIDITY("access_token_validity"),

    /**
     * 客户端特定的RefreshToken超时时间
     */
    REFRESH_TOKEN_VALIDITY("refresh_token_validity"),

    /**
     * 客户端附加信息
     */
    ADDITIONAL_INFORMATION("additional_information"),

    /**
     * 自动授权范围列表
     */
    AUTO_APPROVE("auto_approve"),

    /**
     * 
     */
    LAST_UPDATE_DATE("last_update_date");

    private String value;

    OauthClientEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
