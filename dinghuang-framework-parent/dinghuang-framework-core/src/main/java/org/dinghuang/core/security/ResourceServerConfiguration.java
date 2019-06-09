package org.dinghuang.core.security;

import org.dinghuang.core.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import static javax.servlet.DispatcherType.REQUEST;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/9
 */
@Configuration
@ConditionalOnProperty(prefix = "dinghuang.security.oauth2", name = "clientEnabled", havingValue = "true")
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private OAuth2Properties oAuth2Properties;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v1/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean someFilterRegistration(JwtTokenFilter jwtTokenFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwtTokenFilter);
        registration.addUrlPatterns("/v1/*");
        registration.setName("jwtTokenFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        registration.setDispatcherTypes(REQUEST);
        return registration;
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(tokenServices());
    }

    /**
     * DefaultTokenService Bean
     *
     * @return DefaultTokenService对象
     */
    private DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    private TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式 生成签名的key
        if(oAuth2Properties==null){
            accessTokenConverter.setSigningKey("default");
        }else{
            accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());

        }
        return accessTokenConverter;
    }

}