package org.dinghuang.oauth.config;

import org.dinghuang.oauth.properties.OAuth2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
@EnableConfigurationProperties(OAuth2Properties.class)
public class OAuth2CoreConfig {
}
