package org.dinghuang.core.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.Filter;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/10
 */
@Configuration
public class FilteringRegistrationConfiguration {
    /**
     * 调整securityFilter顺序
     *
     * @param securityFilter securityFilter
     * @return FilterRegistrationBean
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean securityFilterChain(
            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
                    Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(Integer.MAX_VALUE - 2);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }

    /**
     * 注入HystrixRequestContextEnablerFilter顺序
     *
     * @return FilterRegistrationBean
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean userInsertingMdcFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HystrixRequestContextEnablerFilter userFilter = new HystrixRequestContextEnablerFilter();
        registrationBean.setFilter(userFilter);
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        return registrationBean;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean setterInsertingMdcFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SecurityContextHystrixRequestVariableSetterFilter userFilter =
                new SecurityContextHystrixRequestVariableSetterFilter();
        registrationBean.setFilter(userFilter);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;

    }
}
