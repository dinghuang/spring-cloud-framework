package org.dinghuang.core.security.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.core.security.UserFactory;
import org.dinghuang.core.security.enums.UserEnum;
import org.dinghuang.core.security.model.User;
import org.dinghuang.core.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Spring security user实现
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserEnum.USERNAME.value(), username);
        User user = userRepository.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No dto found with username '%s'.", username));
        } else {
            return UserFactory.create(user);
        }
    }

}
