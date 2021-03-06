package org.dinghuang.oauth.properties;

import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private String redirectUrl;

    private String scopes;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;
}
