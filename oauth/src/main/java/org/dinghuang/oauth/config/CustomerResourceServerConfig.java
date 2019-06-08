package org.dinghuang.oauth.config;

//import org.dinghuang.oauth.entrypoint.CustomerAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 配置资源服务器
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
@EnableResourceServer
public class CustomerResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler appLoginFailureHandler;

    @Autowired
    private PermitAllSecurityConfig permitAllSecurityConfig;

    /**
     * 这里设置需要token验证的url
     * 这些url可以在WebSecurityConfigurerAdapter中排查掉，
     * 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行
     * WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //登录成功处理器
                .successHandler(authenticationSuccessHandler)
                //登录失败处理器
                .failureHandler(appLoginFailureHandler)
                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomerAuthenticationEntryPoint())
//                .and()
                //安全过滤器
                .apply(permitAllSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/forbidden").hasRole("ADMIN")
                .antMatchers("/permitAll").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.authenticationEntryPoint(new CustomerAuthenticationEntryPoint());
    }
}
