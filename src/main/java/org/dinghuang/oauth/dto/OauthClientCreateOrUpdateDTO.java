package org.dinghuang.oauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 认证客户端UpdateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Data
@ApiModel(description = "认证客户端")
public class OauthClientCreateOrUpdateDTO {

    @ApiModelProperty(notes = "")
    private Long id;

    @ApiModelProperty(notes = "客户端名称")
    @Length(max = 32, min = 0, message = "name:认证客户端的客户端名称长度不能超过32")
    @NotBlank(message = "name:客户端名称不能为空")
    private String name;

    @ApiModelProperty(notes = "组织ID")
    @NotNull(message = "organizationId:组织ID不能为空")
    private Long organizationId;

    @ApiModelProperty(notes = "资源ID列表，目前只使用default")
    @Length(max = 32, min = 0, message = "resourceIds:认证客户端的资源ID列表，目前只使用default长度不能超过32")
    private String resourceIds;

    @ApiModelProperty(notes = "客户端秘钥")
    @Length(max = 255, min = 0, message = "secret:认证客户端的客户端秘钥长度不能超过255")
    private String secret;

    @ApiModelProperty(notes = "Oauth授权范围")
    @Length(max = 32, min = 0, message = "scope:认证客户端的Oauth授权范围长度不能超过32")
    private String scope;

    @ApiModelProperty(notes = "支持的授权类型列表")
    @Length(max = 255, min = 0, message = "authorizedGrantTypes:认证客户端的支持的授权类型列表长度不能超过255")
    private String authorizedGrantTypes;

    @ApiModelProperty(notes = "授权重定向URL")
    @Length(max = 128, min = 0, message = "webServerRedirectUri:认证客户端的授权重定向URL长度不能超过128")
    private String webServerRedirectUri;

    @ApiModelProperty(notes = "客户端特定的AccessToken超时时间")
    private Long accessTokenValidity;

    @ApiModelProperty(notes = "客户端特定的RefreshToken超时时间")
    private Long refreshTokenValidity;

    @ApiModelProperty(notes = "客户端附加信息")
    @Length(max = 1024, min = 0, message = "additionalInformation:认证客户端的客户端附加信息长度不能超过1024")
    private String additionalInformation;

    @ApiModelProperty(notes = "自动授权范围列表")
    @Length(max = 32, min = 0, message = "autoApprove:认证客户端的自动授权范围列表长度不能超过32")
    private String autoApprove;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "")
    private Date lastUpdateDate;

    @ApiModelProperty(notes = "")
    private Long id;

    @ApiModelProperty(notes = "客户端名称")
    @Length(max = 32, min = 0, message = "name:认证客户端的客户端名称长度不能超过32")
    @NotBlank(message = "name:客户端名称不能为空")
    private String name;

    @ApiModelProperty(notes = "组织ID")
    @NotNull(message = "organizationId:组织ID不能为空")
    private Long organizationId;

    @ApiModelProperty(notes = "资源ID列表，目前只使用default")
    @Length(max = 32, min = 0, message = "resourceIds:认证客户端的资源ID列表，目前只使用default长度不能超过32")
    private String resourceIds;

    @ApiModelProperty(notes = "客户端秘钥")
    @Length(max = 255, min = 0, message = "secret:认证客户端的客户端秘钥长度不能超过255")
    private String secret;

    @ApiModelProperty(notes = "Oauth授权范围")
    @Length(max = 32, min = 0, message = "scope:认证客户端的Oauth授权范围长度不能超过32")
    private String scope;

    @ApiModelProperty(notes = "支持的授权类型列表")
    @Length(max = 255, min = 0, message = "authorizedGrantTypes:认证客户端的支持的授权类型列表长度不能超过255")
    private String authorizedGrantTypes;

    @ApiModelProperty(notes = "授权重定向URL")
    @Length(max = 128, min = 0, message = "webServerRedirectUri:认证客户端的授权重定向URL长度不能超过128")
    private String webServerRedirectUri;

    @ApiModelProperty(notes = "客户端特定的AccessToken超时时间")
    private Long accessTokenValidity;

    @ApiModelProperty(notes = "客户端特定的RefreshToken超时时间")
    private Long refreshTokenValidity;

    @ApiModelProperty(notes = "客户端附加信息")
    @Length(max = 1024, min = 0, message = "additionalInformation:认证客户端的客户端附加信息长度不能超过1024")
    private String additionalInformation;

    @ApiModelProperty(notes = "自动授权范围列表")
    @Length(max = 32, min = 0, message = "autoApprove:认证客户端的自动授权范围列表长度不能超过32")
    private String autoApprove;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "")
    private Date lastUpdateDate;

    @ApiModelProperty(notes = "乐观锁版本号")
    private Long objectVersionNumber;


}
