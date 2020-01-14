package org.dinghuang.oauth.config;

import org.dinghuang.oauth.filrer.PermitAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
public class PermitAllSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PermitAuthenticationFilter permitAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(permitAuthenticationFilter, OAuth2AuthenticationProcessingFilter.class);
    }
}
