package org.dinghuang.core.security;

import org.dinghuang.core.security.model.SecurityUser;
import org.dinghuang.core.security.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class UserFactory {
    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getUuid(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getLastPasswordReset(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
