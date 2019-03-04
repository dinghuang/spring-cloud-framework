package org.dinghuang.security.service.impl;

import org.dinghuang.security.service.ResourceRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * spring security api url认证拦截规则
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Component
public class ResourceRuleServiceImpl implements ResourceRuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRuleServiceImpl.class);

    @Value("${security.enabled}")
    private boolean securityEnabled;

    @Value("${jwt.public.uris}")
    private String[] publicUris;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Value("${jwt.route.authentication.refresh}")
    private String refreshPath;

    private Map<String, Set<String>> pathToRole = new HashMap<>();
    private Map<String, Set<String>> roleToPath = new HashMap<>();

    private static final String SIGN = ",";
    private static final String SLASH = "/";

    @Override
    public Map<String, Set<String>> getPathToRole() {
        return Collections.unmodifiableMap(pathToRole);
    }

    @Override
    public Map<String, Set<String>> getRoleToPath() {
        return Collections.unmodifiableMap(roleToPath);
    }

    @PostConstruct
    public void init() {
        //todo 加载api以及角色授权信息等 pathToRole/roleToPath
    }


    @Override
    @SuppressWarnings("unchecked")
    public void resourceRuleHandle(HttpSecurity httpSecurity) throws Exception {
        //todo 这里还需要别的实现
        ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry =
                httpSecurity.authorizeRequests();
        if (securityEnabled) {
            pathToRole.forEach((path, value) -> {
                String[] attr = path.split(SIGN);
                String httpMethod = attr[0];
                String url = attr[1];
                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                        .antMatchers(HttpMethod.valueOf(httpMethod), url))
                        .hasAnyRole(value.stream().map(role -> role.replace("ROLE_", "")).distinct().toArray(String[]::new));
            });
            for (String publicUri : publicUris) {
                if (!publicUri.startsWith(SLASH)) {
                    publicUri = SLASH + publicUri;
                }
                if (publicUri.indexOf("*") == -1) {
                    publicUri = "/**" + publicUri;
                    ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                            .antMatchers(publicUri + "*")).permitAll();
                    if (!publicUri.endsWith(SLASH)) {
                        publicUri = publicUri + SLASH;
                    }
                    publicUri = publicUri + "**";
                }
                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                        .antMatchers(publicUri)).permitAll();
            }
            if (!authenticationPath.startsWith(SLASH)) {
                authenticationPath = SLASH + authenticationPath;
            }
            authenticationPath = "/**" + authenticationPath;
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                    .antMatchers(authenticationPath)).permitAll();
            if (!refreshPath.startsWith(SLASH)) {
                refreshPath = SLASH + refreshPath;
            }
            refreshPath = "/**" + refreshPath;
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                    .antMatchers(refreshPath)).permitAll();
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                    .antMatchers("/**")).hasRole("SUPERUSER");
        } else {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                    .antMatchers("/**")).permitAll();
        }
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) expressionInterceptUrlRegistry
                .anyRequest()).authenticated();
    }

}
