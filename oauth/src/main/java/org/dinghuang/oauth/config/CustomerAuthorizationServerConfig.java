package org.dinghuang.oauth.config;

import com.google.common.net.HttpHeaders;
import org.apache.commons.lang.ArrayUtils;
import org.dinghuang.oauth.entrypoint.CustomerAuthenticationEntryPoint;
import org.dinghuang.oauth.properties.OAuth2ClientProperties;
import org.dinghuang.oauth.properties.OAuth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Autowired
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
     * @param endpoints endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        //扩展token返回结果
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancerList);
            //jwt
            endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
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
                        .secret(config.getClientSecret())
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(60 * 60 * 24 * 15)
                        //OAuth2支持的验证模式 密码模式、授权码模式、token刷新
                        .authorizedGrantTypes("refresh_token", "password", "authorization_code")
                        .scopes("all");
            }
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) {
        //todo 配置oauth2服务跨域
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
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));
        authorizationServerSecurityConfigurer.authenticationEntryPoint(new CustomerAuthenticationEntryPoint());
    }
}
