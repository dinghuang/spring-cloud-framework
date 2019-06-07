package org.dinghuang.oauth.config;

import org.dinghuang.oauth.handler.JwtTokenEnhancer;
import org.dinghuang.oauth.properties.OAuth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * token储存配置
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
public class TokenStoreConfig {

    /**
     * redis连接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 用于存放token
     *
     * @return TokenStore
     */
    @Bean
    @ConditionalOnProperty(prefix = "dinghuang.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * jwt TOKEN配置信息
     */
    @Configuration
    @ConditionalOnProperty(prefix = "dinghuang.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        @Autowired
        private OAuth2Properties oAuth2Properties;

        /**
         * 使用jwtTokenStore存储token
         *
         * @return TokenStore
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 用于生成jwt
         *
         * @return JwtAccessTokenConverter
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式 生成签名的key
            //todo 签名的key
            accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());
            return accessTokenConverter;
        }
    }
}
