package org.dinghuang.oauth.handler;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class CustomerUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
