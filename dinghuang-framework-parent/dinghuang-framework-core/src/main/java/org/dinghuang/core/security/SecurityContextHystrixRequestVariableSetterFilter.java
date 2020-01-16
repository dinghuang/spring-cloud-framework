//package org.dinghuang.core.security;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import javax.servlet.*;
//import java.io.IOException;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/6/10
// */
//public class SecurityContextHystrixRequestVariableSetterFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            SecurityContextHystrixRequestVariable.getInstance().set(SecurityContextHolder.getContext());
//        }
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//        //do not something
//    }
//
//    @Override
//    public void destroy() {
//        //do not something
//    }
//}
//
