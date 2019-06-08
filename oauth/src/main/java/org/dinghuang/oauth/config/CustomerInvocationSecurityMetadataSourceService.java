//package org.dinghuang.oauth.config;
//
//import org.dinghuang.oauth.infra.repository.RolePermissionsRepository;
//import org.dinghuang.oauth.mapper.RolePermissionsMapper;
//import org.dinghuang.oauth.model.RolePermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/6/8
// */
//@Component
//public class CustomerInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
//
//    @Autowired
//    private RolePermissionsRepository rolePermissionsRepository;
//
//    /**
//     * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
//     */
//    private static ConcurrentHashMap<String, Collection<ConfigAttribute>> MAP = null;
//
//
//    /**
//     * 返回请求的资源需要的角色
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        //object 中包含用户请求的request 信息
//        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
//        for (Iterator<String> it = MAP.keySet().iterator(); it.hasNext(); ) {
//            String url = it.next();
//            if (new AntPathRequestMatcher(url).matches(request)) {
//                return MAP.get(url);
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        //初始化 所有资源 对应的角色
//        loadResourceDefine();
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//    /**
//     * 初始化 所有资源 对应的角色
//     */
//    private void loadResourceDefine() {
//        MAP = new ConcurrentHashMap<>(16);
//        //权限资源 和 角色对应的表  也就是 角色权限 中间表
//        List<RolePermissions> rolePermissionsList = RolePermissionsMapper.INSTANCE.doToRolePermissions(rolePermissionsRepository.selectList(null));
//        //某个资源 可以被哪些角色访问
//        rolePermissionsList.forEach(rolePermissions -> {
//            String url = rolePermissions.getUrl();
//            String roleName = rolePermissions.getRoleName();
//            ConfigAttribute role = new SecurityConfig(roleName);
//            if (MAP.containsKey(url)) {
//                MAP.get(url).add(role);
//            } else {
//                List<ConfigAttribute> list = new ArrayList<>();
//                list.add(role);
//                MAP.put(url, list);
//            }
//        });
//    }
//
//
//}
