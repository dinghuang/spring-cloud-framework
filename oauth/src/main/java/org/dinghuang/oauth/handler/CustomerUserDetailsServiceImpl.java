package org.dinghuang.oauth.handler;

import org.dinghuang.core.exception.CommonValidateException;
import org.dinghuang.oauth.infra.repository.RoleRepository;
import org.dinghuang.oauth.infra.repository.UserRepository;
import org.dinghuang.oauth.mapper.RoleMapper;
import org.dinghuang.oauth.mapper.UserMapper;
import org.dinghuang.oauth.model.Role;
import org.dinghuang.oauth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class CustomerUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //查数据库
        User user = UserMapper.INSTANCE.doToUser(userRepository.queryByName(username));
        if (null != user) {
            List<Role> roles = RoleMapper.INSTANCE.doToRoles(roleRepository.queryRolesByUserId(user.getId()));
            user.setAuthorities(roles);
        }else{
            throw new CommonValidateException("用户名不存在");
        }
        return user;
    }
}
