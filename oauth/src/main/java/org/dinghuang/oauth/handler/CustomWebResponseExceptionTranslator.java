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

    /**
     * 自定义登录或者鉴权失败时的返回信息
     *
     * @param e e
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        //todo 可以做一下返回信息
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new CustomOauthException(oAuth2Exception.getMessage()));
    }
}
