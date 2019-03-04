package org.dinghuang.security.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        Map<String, Object> message = new HashMap<>();
        message.put("message", ExceptionUtils.getMessage(authException));
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new ResponseEntity<>(message, HttpStatus.FORBIDDEN)));
    }
}
