package org.dinghuang.oauth.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class CustomerLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String message;
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
            response.getWriter().write(JSON.toJSONString(new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED)));
        }
    }
}
