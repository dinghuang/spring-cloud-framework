//package org.dinghuang.security.model;
//
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.util.SerializationUtils;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//
//import javax.persistence.Transient;
//import java.util.Date;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/3/1
// */
//@Data
//@TableName("oauth_access_token")
//public class AccessToken {
//
//    private String tokenId;
//
//    private String authenticationId;
//
//    private String userName;
//
//    private String clientId;
//
//    private String refreshToken;
//
//    @Transient
//    private OAuth2AccessToken value;
//
//    @Transient
//    private OAuth2Authentication auth2Authentication;
//
//    @Transient
//    private Date lastTime;
//
//    @JsonIgnore
//    private byte[] authentication;
//
//    @JsonIgnore
//    private byte[] token;
//
//    public void setValue(OAuth2AccessToken value) {
//        this.value = value;
//        this.token = SerializationUtils.serialize(value);
//    }
//
//    public void setToken(byte[] token) {
//        this.token = token;
//        this.value = SerializationUtils.deserialize(token);
//    }
//
//    public void setAuth2Authentication(OAuth2Authentication oauth2Authentication) {
//        this.auth2Authentication = oauth2Authentication;
//        this.authentication = SerializationUtils.serialize(oauth2Authentication);
//    }
//}
