package org.dinghuang.oauth.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.infra.dataobject.enums.UserEnum;
import org.dinghuang.oauth.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    @SuppressWarnings("unchecked")
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        //todo 可以存放用户信息在token中
        LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
        String userName = linkedHashMap.get("username");
        User user = (User) authentication.getUserAuthentication().getPrincipal();
        QueryWrapper<UserDO> userDOQueryWrapper = new QueryWrapper<>();
        userDOQueryWrapper.eq(true, UserEnum.ACCOUNT.value(), userName);
        UserDO userDO = userRepository.selectOne(userDOQueryWrapper);
        //自定义一些token属性
        final Map<String, Object> additionalInformation = new HashMap<>(2);
        additionalInformation.put("user", userDO);
        additionalInformation.put("roles", user.getAuthorities());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
