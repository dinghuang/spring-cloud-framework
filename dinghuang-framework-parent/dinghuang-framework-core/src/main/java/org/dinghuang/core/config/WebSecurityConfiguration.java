package org.dinghuang.core.config;

import org.dinghuang.core.security.config.*;
import org.dinghuang.core.security.properties.OauthProperties;
import org.dinghuang.core.security.service.ResourceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@ConditionalOnWebApplication
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(OauthProperties.class)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String SIGN = ",";

    @Autowired(required = false)
    private ResourceRuleService resourceRuleService;


    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationDetailSource authenticationDetailSource;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Value("${jwt.route.authentication.loginPath}")
    private String loginPath;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPointBean() {
        return new RestAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String[] authenticationPaths = authenticationPath.split(SIGN);
        httpSecurity
                .authorizeRequests()
                .antMatchers(authenticationPaths)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage(loginPath)
                .loginProcessingUrl(authenticationPath)
                .authenticationDetailsSource(authenticationDetailSource)
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                //deleteCookies:指定退出登录后需要删除的cookie名称，多个cookie之间以逗号分隔 ; invalidateHttpSession 默认为true,用户在退出后Http session失效
                .logout().deleteCookies("access_token").invalidateHttpSession(true)
                // 用来自定义退出成功后的操作
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .csrf()
                .disable()
                // Custom JWT based authentication
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPointBean())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        if (resourceRuleService != null) {
            resourceRuleService.resourceRuleHandle(httpSecurity);
        }

    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString();
            //return new BigInteger(1, md.digest()).toString(16); //old
        } catch (Exception e) {
            throw new RuntimeException("MD 5 encryption error");
        }
    }

    public static void main(String[] args) {
        //生成md5
        System.out.println(getMD5("1qaz2wsx"));
        //生成加密密码
        System.out.println(new BCryptPasswordEncoder().encode("e10adc3949ba59abbe56e057f20f883e"));

        System.out.println(new BCryptPasswordEncoder().encode(getMD5("1qaz2wsx")));

    }
}
