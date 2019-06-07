package org.dinghuang.oauth.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证入口点
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        Map<String, Object> map = new HashMap<>(4);
        map.put("error", "401");
        map.put("message", authException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}