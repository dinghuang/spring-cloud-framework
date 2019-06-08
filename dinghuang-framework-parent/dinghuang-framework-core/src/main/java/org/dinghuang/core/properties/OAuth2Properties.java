package org.dinghuang.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@ConfigurationProperties(prefix = "dinghuang.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey;

    private OAuth2ClientProperties[] clients;

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
