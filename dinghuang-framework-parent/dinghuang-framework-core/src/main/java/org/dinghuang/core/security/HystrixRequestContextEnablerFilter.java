package org.dinghuang.core.security;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/10
 */
public class HystrixRequestContextEnablerFilter implements Filter {

    public static final String AUTHORIZATION = "Authorization";
    public static final HystrixRequestVariableDefault<String> authorization = new HystrixRequestVariableDefault();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }

        String authorizationString = httpServletRequest.getHeader(AUTHORIZATION);
        authorization.set(authorizationString);
        chain.doFilter(request, response);
        context.shutdown();
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // do not something
    }

    @Override
    public void destroy() {
        // do not something
    }
}
