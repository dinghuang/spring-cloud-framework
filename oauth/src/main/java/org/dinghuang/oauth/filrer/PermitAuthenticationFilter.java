package org.dinghuang.oauth.filrer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 许可认证过滤器
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Configuration
public class PermitAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermitAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //todo 判断token是否正确
        LOGGER.info("当前访问的地址:{}", request.getRequestURI());
        if ("/permitAll".equals(request.getRequestURI())) {

            request = new HttpServletRequestWrapper(request) {

                private Set<String> headerNameSet;

                @Override
                public Enumeration<String> getHeaderNames() {
                    if (headerNameSet == null) {
                        // first time this method is called, cache the wrapped request's header names:
                        headerNameSet = new HashSet<>();
                        Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
                        while (wrappedHeaderNames.hasMoreElements()) {
                            String headerName = wrappedHeaderNames.nextElement();
                            if (!"Authorization".equalsIgnoreCase(headerName)) {
                                headerNameSet.add(headerName);
                            }
                        }
                    }
                    return Collections.enumeration(headerNameSet);
                }

                @Override
                public Enumeration<String> getHeaders(String name) {
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return Collections.emptyEnumeration();
                    }
                    return super.getHeaders(name);
                }

                @Override
                public String getHeader(String name) {
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return null;
                    }
                    return super.getHeader(name);
                }
            };

        }
        filterChain.doFilter(request, response);

    }
}
