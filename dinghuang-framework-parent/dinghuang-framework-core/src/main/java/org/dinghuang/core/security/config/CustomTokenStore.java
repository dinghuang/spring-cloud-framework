//package org.dinghuang.core.security.config;
//
//import lombok.Data;
//import org.dinghuang.core.security.properties.OauthProperties;
//import org.dinghuang.core.security.repository.AccessTokenRepository;
//import org.dinghuang.core.security.service.CustomAuthenticationKeyGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.sql.DataSource;
//import java.util.Date;
//import java.util.HashMap;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/3/1
// */
//@Service
//@Data
//public class CustomTokenStore extends JdbcTokenStore {
//
//    private static final String CREATION_TIME = "createTime";
//    private static final String SESSION_ID = "sessionId";
//
//    @Autowired
//    private OauthProperties oauthProperties;
//
//    @Autowired
//    private AccessTokenRepository accessTokenRepository;
//
//    @Autowired
//    private CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator;
//
//    @Autowired
//    public CustomTokenStore(DataSource dataSource,
//                            CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator) {
//        super(dataSource);
//        setAuthenticationKeyGenerator(customAuthenticationKeyGenerator);
//    }
//
//    public void setAuthenticationKeyGenerator(CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator) {
//        this.customAuthenticationKeyGenerator = customAuthenticationKeyGenerator;
//        super.setAuthenticationKeyGenerator(customAuthenticationKeyGenerator);
//    }
//
//    @Override
//    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
//        if (oauthProperties.isEnabledSingleLogin() && !authentication.isClientOnly()) {
//            String key = customAuthenticationKeyGenerator.extractKey(authentication);
//            String username = authentication.getName();
//            String clientId = authentication.getOAuth2Request().getClientId();
//            accessTokenRepository.selectTokens(username, clientId, key);
//            accessTokenRepository.deleteTokens(username, clientId, key);
//
//        }
//        return super.getAccessToken(authentication);
//    }
//
//    @Override
//    public void removeAccessToken(String tokenValue) {
//        String tokenId = extractTokenKey(tokenValue);
//        accessTokenRepository.selectById(tokenId);
//        super.removeAccessToken(tokenValue);
//    }
//
//    @Override
//    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//        HashMap<String, Object> additionalInfo = new HashMap<>();
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        String sessionId = "";
//        if (attr != null) {
//            sessionId = attr.getRequest().getSession(true).getId();
//        }
//        additionalInfo.put(CREATION_TIME, new Date());
//        additionalInfo.put(SESSION_ID, sessionId);
//        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInfo);
//        super.storeAccessToken(token, authentication);
//    }
//}
//
