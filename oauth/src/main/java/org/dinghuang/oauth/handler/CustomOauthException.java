package org.dinghuang.oauth.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}
