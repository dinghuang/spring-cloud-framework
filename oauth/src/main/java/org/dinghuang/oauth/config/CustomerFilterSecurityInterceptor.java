//package org.dinghuang.oauth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import java.io.IOException;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/6/8
// */
//@Component
//@Lazy
//public class CustomerFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
//
//    @Autowired
//    private FilterInvocationSecurityMetadataSource securityMetadataSource;
//
//    @Autowired
//    public void setMyAccessDecisionManager(CustomerAccessDecisionManager customerAccessDecisionManager) {
//        super.setAccessDecisionManager(customerAccessDecisionManager);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
//        invoke(fi);
//    }
//
//    private void invoke(FilterInvocation fi) throws IOException, ServletException {
//        InterceptorStatusToken token = super.beforeInvocation(fi);
//        try {
//            //执行下一个拦截器
//            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//        } finally {
//            super.afterInvocation(token, null);
//        }
//    }
//
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//
//        return this.securityMetadataSource;
//    }
//
//
//}
