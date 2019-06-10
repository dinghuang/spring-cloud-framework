package org.dinghuang.core.security;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/10
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            template.header(org.dinghuang.core.security.HystrixRequestContextEnablerFilter.AUTHORIZATION, org.dinghuang.core.security.HystrixRequestContextEnablerFilter.authorization.get());
        }
    }
}

