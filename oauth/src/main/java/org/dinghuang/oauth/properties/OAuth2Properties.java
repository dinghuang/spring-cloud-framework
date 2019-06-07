package org.dinghuang.oauth.properties;

import lombok.Data;
import org.dinghuang.oauth.properties.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Data
@ConfigurationProperties(prefix = "dinghuang.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "dinghuang";

    private OAuth2ClientProperties[] clients = {};
}
