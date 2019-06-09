package org.dinghuang.oauth.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 认证客户端DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Data
@TableName("oauth_client")
@EqualsAndHashCode(callSuper = true)
public class OauthClientDO extends BaseModel {

    /**
     * 
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 客户端名称
     */
    private String name;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 资源ID列表，目前只使用default
     */
    private String resourceIds;

    /**
     * 客户端秘钥
     */
    private String secret;

    /**
     * Oauth授权范围
     */
    private String scope;

    /**
     * 支持的授权类型列表
     */
    private String authorizedGrantTypes;

    /**
     * 授权重定向URL
     */
    private String webServerRedirectUri;

    /**
     * 客户端特定的AccessToken超时时间
     */
    private Long accessTokenValidity;

    /**
     * 客户端特定的RefreshToken超时时间
     */
    private Long refreshTokenValidity;

    /**
     * 客户端附加信息
     */
    private String additionalInformation;

    /**
     * 自动授权范围列表
     */
    private String autoApprove;

    /**
     * 
     */
    private Date lastUpdateDate;

    /**
     * 
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 客户端名称
     */
    private String name;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 资源ID列表，目前只使用default
     */
    private String resourceIds;

    /**
     * 客户端秘钥
     */
    private String secret;

    /**
     * Oauth授权范围
     */
    private String scope;

    /**
     * 支持的授权类型列表
     */
    private String authorizedGrantTypes;

    /**
     * 授权重定向URL
     */
    private String webServerRedirectUri;

    /**
     * 客户端特定的AccessToken超时时间
     */
    private Long accessTokenValidity;

    /**
     * 客户端特定的RefreshToken超时时间
     */
    private Long refreshTokenValidity;

    /**
     * 客户端附加信息
     */
    private String additionalInformation;

    /**
     * 自动授权范围列表
     */
    private String autoApprove;

    /**
     * 
     */
    private Date lastUpdateDate;


}
