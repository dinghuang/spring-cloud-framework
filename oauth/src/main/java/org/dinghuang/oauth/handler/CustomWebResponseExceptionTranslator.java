package org.dinghuang.oauth.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;


/**
 * Web响应异常转换器
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new CustomOauthException(oAuth2Exception.getMessage()));
    }
}
