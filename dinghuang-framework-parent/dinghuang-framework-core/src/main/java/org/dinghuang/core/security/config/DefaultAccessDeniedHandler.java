package org.dinghuang.core.security.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        Map<String, Object> message = new HashMap<>(1);
        message.put("message", ExceptionUtils.getMessage(accessDeniedException));
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new ResponseEntity<>(message, HttpStatus.FORBIDDEN)));
    }
}
