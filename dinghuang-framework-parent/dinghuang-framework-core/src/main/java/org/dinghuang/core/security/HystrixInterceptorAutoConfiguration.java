package org.dinghuang.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/10
 */
@Configuration
public class HystrixInterceptorAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new org.dinghuang.core.security.HystrixHeaderInterceptor());
    }
}

