package org.dinghuang.oauth.config;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.google.common.net.HttpHeaders;
import org.dinghuang.oauth.entrypoint.CustomerAuthenticationEntryPoint;
import org.dinghuang.oauth.properties.OAuth2ClientProperties;
import org.dinghuang.oauth.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置认证服务器
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
@EnableAuthorizationServer
public class CustomerAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthorizationServerConfig.class);

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    /**
     * 授权服务器端点配置程序
     *
     * @param authorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) {
        authorizationServerEndpointsConfigurer.tokenStore(tokenStore)
                //开启密码授权类型
                .authenticationManager(authenticationManager)
                //要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService);
        //扩展token返回结果
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancerList);
            //jwt
            authorizationServerEndpointsConfigurer.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
        //自定义登录或者鉴权失败时的返回信息
        authorizationServerEndpointsConfigurer.exceptionTranslator(customWebResponseExceptionTranslator);
    }

    /**
     * 配置客户端一些信息
     *
     * @param clients clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder build = clients.inMemory();
        //todo 配置不同客户端的token验证策略
        if (ArrayUtils.isNotEmpty(oAuth2Properties.getClients())) {
            for (OAuth2ClientProperties config : oAuth2Properties.getClients()) {
                build.withClient(config.getClientId())
                        .secret("{bcrypt}" + new BCryptPasswordEncoder().encode(config.getClientSecret()))
                        .resourceIds(config.getClientId())
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(config.getRefreshTokenValiditySeconds())
                        //OAuth2支持的验证模式 密码模式、授权码模式、token刷新
                        .authorizedGrantTypes("refresh_token", "password", "authorization_code")
                        .redirectUris(config.getRedirectUrl())
                        .scopes(config.getScopes());
            }
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) {
        //配置oauth2服务跨域
        CorsConfigurationSource source = request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedOrigin(request.getHeader(HttpHeaders.ORIGIN));
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
        };

        authorizationServerSecurityConfigurer.tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));
        authorizationServerSecurityConfigurer.authenticationEntryPoint(new CustomerAuthenticationEntryPoint());
    }
}
