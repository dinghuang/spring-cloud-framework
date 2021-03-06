package org.dinghuang.oauth.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.oauth.handler.CustomerExpiredSessionHandler;
import org.dinghuang.oauth.handler.CustomerLoginOutSuccessHandler;
import org.dinghuang.oauth.infra.dataobject.RoleDO;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.infra.dataobject.enums.UserEnum;
import org.dinghuang.oauth.infra.repository.RoleRepository;
import org.dinghuang.oauth.infra.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWebSecurityConfigurerAdapter.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and()
                .httpBasic().and().csrf().disable()
                .sessionManagement()
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .expiredSessionStrategy(new CustomerExpiredSessionHandler())
//                .sessionRegistry(sessionRegistry())
//                .and()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll().logoutSuccessHandler(logoutSuccessHandler());
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        //配置用户来源于数据库
        auth.userDetailsService(userDetailsService())
                //todo 自定义密码策略
                .passwordEncoder(new BCryptPasswordEncoder());
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

    @Bean(name = "authenticationManagerBean")
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
                String[] roles = roleRepository.queryRolesByUserId(userDO.getId()).stream().map(RoleDO::getName).toArray(String[]::new);
                return new User(userDO.getName(), userDO.getPassword(), AuthorityUtils.createAuthorityList(roles));
            } else {
                throw new UsernameNotFoundException("用户[" + name + "]不存在");
            }
        };
    }

}
