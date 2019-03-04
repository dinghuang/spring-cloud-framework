package org.dinghuang.security.service;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public interface UserPermissionService {

    /**
     * todo 权限校验的实现
     *
     * @param accountId accountId
     * @return countPermissionByAccountId
     */
    int countPermissionByAccountId(String accountId);
}
