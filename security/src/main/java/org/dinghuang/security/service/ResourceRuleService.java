package org.dinghuang.security.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Map;
import java.util.Set;

/**
 * 资源拦截规则接口
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public interface ResourceRuleService {
    Map<String, Set<String>> getPathToRole();

    Map<String, Set<String>> getRoleToPath();

    /**
     * todo 资源拦截规则
     *
     * @param httpSecurity httpSecurity
     * @throws Exception Exception
     */
    void resourceRuleHandle(HttpSecurity httpSecurity) throws Exception;
}
