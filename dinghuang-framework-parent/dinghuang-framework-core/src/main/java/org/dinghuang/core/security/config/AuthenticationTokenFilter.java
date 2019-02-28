package org.dinghuang.core.security.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.dinghuang.core.security.exception.AuthenticationException;
import org.dinghuang.core.security.service.UserPermissionService;
import org.dinghuang.core.security.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 应用于手机端的令牌标志
     */
    private static final String AUTH_TOKEN_HEADER = "AuthToken";
    private static final int AUTH_TOKEN_EXPIRED = 60 * 60 * 1000 * 12;
    private static final String[] PATTERN = {"yyyyMMddHHmmss"};
    private static final String SIGN = ",";
    private static final String INVOCATION_FLAG = "InvocationFlag";


    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Value("${jwt.route.authentication.refresh}")
    private String refreshPath;

    @Value("${jwt.public.uris}")
    private String[] publicUris;

    @Value("${security.enabled}")
    private boolean securityEnabled;

    @Value("${security.action.enabled}")
    private boolean securityActionEnabled;

    /**
     * 顶级管理员，跳过权限校验
     */
    @Value("${security.permission.superadmin}")
    private String superadmin;

    @Value("${dinghuang.swagger.enabled}")
    private boolean swaggerEnabled;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    UserPermissionService userPermissionService;


    @PostConstruct
    public void init() {
        for (String publicUri : publicUris) {
            LOGGER.info("public resources: {}", publicUri);
        }
    }

    private boolean isPublic(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.endsWith(authenticationPath)) {
            return true;
        }
        if (uri.endsWith(refreshPath)) {
            return true;
        }
        if (publicUris == null) {
            return false;
        }
        //如果开启了swagger，则是公共页面
        if (swaggerEnabled) {
            return true;
        }
        for (String publicUri : publicUris) {
            boolean match = PATH_MATCHER.match(publicUri, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证手机端令牌,  12小时失效
     *
     * @param request   request
     * @param accountId accountId
     * @return 手机端true
     */
    private boolean isMobile(HttpServletRequest request, String accountId) {
        String authToken = request.getHeader(AUTH_TOKEN_HEADER);
        if (StringUtils.isBlank(authToken)) {
            return false;
        }
        String[] res = tokenUtils.decodeAuthToken(authToken);
        if (res == null || res.length < 3) {
            throw new AuthenticationException("The contents of the Auth Token are illegal");
        } else {
            String time = res[0];
            String userId = res[1];
            String token = res[2];

            if (!userId.equals(accountId)) {
                throw new AuthenticationException("User information error");
            }

            try {
                Date date = DateUtils.parseDate(time, PATTERN);
                long t = System.currentTimeMillis() - date.getTime();
                //12个小时后过期
                if (t - AUTH_TOKEN_EXPIRED > 0) {
                    throw new AuthenticationException("Login information has expired");
                }
            } catch (ParseException e) {
                throw new AuthenticationException("Wrong time information", e);
            }

            String authorization = request.getHeader(tokenHeader);
            if (!token.equals(authorization)) {
                throw new AuthenticationException("Security information invalid");
            }
            return true;
        }
    }

    /**
     * 权限校验
     *
     * @param request   request
     * @param accountId accountId
     * @return boolean
     */
    private boolean havePermission(HttpServletRequest request, String accountId) {
        String invocationFlag = request.getHeader(INVOCATION_FLAG);
        //远程调用时直接通过
        if (StringUtils.isNotBlank(invocationFlag) && Boolean.parseBoolean(invocationFlag)) {
            LOGGER.debug("request header中包含远程调用标识, InvocationFlag:{}", invocationFlag);
            return true;
        } else {
            LOGGER.debug("request header中未包含远程调用标识, InvocationFlag:{}", invocationFlag);
        }

        //不验证页面权限的直接跳过
        if (!securityActionEnabled) {
            return true;
        }
        if (StringUtils.isBlank(accountId)) {
            throw new AuthenticationException("The user id cannot be empty");
        }
        if (userPermissionService == null) {
            throw new AuthenticationException("User authorization authentication Service is not defined");
        }
        int count = userPermissionService.countPermissionByAccountId(accountId);

        return count >= 1;
    }

    /**
     * 权限过滤器
     *
     * @param request  request
     * @param response response
     * @param chain    chain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authToken = httpRequest.getHeader(tokenHeader);
            if (StringUtils.isNotBlank(authToken)) {
                //与用户线程绑定的日志，打印可以用TraceLogger. info("")
                MDC.put(tokenHeader, authToken);
            }
            String username = tokenUtils.getUsernameFromToken(authToken);

            LOGGER.debug("Request path: {}, token={}", httpRequest.getMethod() + SIGN + httpRequest.getRequestURI(), authToken);
            //验证权限
            if (securityEnabled) {
                //未登录
                if (username == null) {
                    //不是公共页面
                    if (!isPublic((HttpServletRequest) request)) {
                        Map<String, Object> map = new HashMap<>(2);
                        map.put("message", "Unauthorized, No access permissions, please in the request header property: " + this.tokenHeader + " specified in the TOKEN");
                        map.put("request_head_property", tokenHeader);
                        ResponseEntity responseEntity = new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
                        ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json; charset=utf-8");
                        response.getWriter().println(JSON.toJSONString(responseEntity, true));
                        return;
                    }
                } else {
                    //已登录
                    //不是管理员
                    if (!username.equals(superadmin)
                            //即不是公共页面
                            && !isPublic((HttpServletRequest) request)
                            //也不是手机端用户
                            && !isMobile((HttpServletRequest) request, username)
                            //又没有权限
                            && !havePermission(httpRequest, username)
                            ) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("message", "Forbidden, Disable access to unauthorized functions");
                        ResponseEntity responseEntity = new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
                        ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json; charset=utf-8");
                        response.getWriter().println(JSON.toJSONString(responseEntity, true));
                        return;
                    }
                }
            }

            //重新设置上下文中的用户信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;
                try {
                    userDetails = userDetailsService.loadUserByUsername(username);
                } catch (Exception e) {
                    throw new AuthenticationException("Users in token: " + username + "no exist", e);
                }
                if (userDetails != null && tokenUtils.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
