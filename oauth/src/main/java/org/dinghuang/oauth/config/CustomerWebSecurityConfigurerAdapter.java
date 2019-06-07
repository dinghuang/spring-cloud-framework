package org.dinghuang.oauth.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.oauth.handler.CustomerExpiredSessionHandler;
import org.dinghuang.oauth.handler.CustomerLoginOutSuccessHandler;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.infra.dataobject.enums.UserEnum;
import org.dinghuang.oauth.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.DigestUtils;

/**
 * Web安全配置程序
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class CustomerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage( "/login" ).failureUrl( "/login-error" )
                .and()
                .authorizeRequests().antMatchers("/session/invalid")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new CustomerExpiredSessionHandler())
                .sessionRegistry(sessionRegistry())
                .and()
                .and()
                .logout()
                .permitAll().logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .csrf().disable();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        //配置用户来源于数据库
        auth.userDetailsService(userDetailsService())
                .passwordEncoder( new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println(charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }
            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                return s.equals( encode );
            }
        } );
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    protected LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomerLoginOutSuccessHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return name -> {
            // 通过用户名获取用户信息
            QueryWrapper<UserDO> userDOQueryWrapper = new QueryWrapper<>();
            userDOQueryWrapper.eq(true, UserEnum.ACCOUNT.value(), name);
            UserDO userDO = userRepository.selectOne(userDOQueryWrapper);
            if (userDO != null) {
                // 创建spring security安全用户
                User user = new User(userDO.getName(), userDO.getPassword(),
                        //todo 角色
                        AuthorityUtils.createAuthorityList(userDO.getRole()));
                return user;
            } else {
                throw new UsernameNotFoundException("用户[" + name + "]不存在");
            }
        };
    }

}
