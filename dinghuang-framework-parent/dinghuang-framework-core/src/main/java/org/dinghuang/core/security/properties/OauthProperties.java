package org.dinghuang.core.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@ConfigurationProperties(prefix = "dinghuang.oauth")
@Data
public class OauthProperties {
    private boolean clearToken = false;

    private boolean enabledSingleLogin = false;

    private int accessTokenValiditySeconds = 24 * 3600;

    public OauthProperties() {
        //
    }
}
