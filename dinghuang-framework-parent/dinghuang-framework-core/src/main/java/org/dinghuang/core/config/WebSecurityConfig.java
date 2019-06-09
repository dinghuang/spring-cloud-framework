//package org.dinghuang.core.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/6/9
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        /**表示所有的访问都必须进行认证处理后才可以正常进行*/
//        http.httpBasic().and().authorizeRequests().anyRequest().fullyAuthenticated();
//        /**所有的Rest服务一定要设置为无状态，以提升操作性能*/
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        /**关闭csrf避免POST请求时出现401异常*/
//        http.csrf().disable();
//        http.authorizeRequests().antMatchers(org.springframework.http.HttpMethod.GET).permitAll();
//    }
//
//}
