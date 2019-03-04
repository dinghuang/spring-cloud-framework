package org.dinghuang.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.dinghuang.security.enums.LoginExceptionEnum;
import org.dinghuang.security.enums.UserEnum;
import org.dinghuang.security.exception.CustomAuthenticationException;
import org.dinghuang.security.model.User;
import org.dinghuang.security.repository.UserRepository;
import org.dinghuang.security.service.LoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Component
@Data
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;
    @Autowired
    private LoginRecord loginRecord;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String username = request.getParameter("username");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("username",
                    username);
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception.getMessage());
            if (exception instanceof CustomAuthenticationException) {
                session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION_PARAMS",
                        ((CustomAuthenticationException) exception).getParameters());
            }

        }
        String message = null;
        if (exception != null) {
            message = exception.getMessage();
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(UserEnum.USERNAME.value(), username);
        User user = userRepository.selectOne(userQueryWrapper);
        if (user != null
                && LoginExceptionEnum.USERNAME_NOT_FOUND_OR_PASSWORD_IS_WRONG.value().equalsIgnoreCase(message)) {
            loginRecord.loginError(user.getUserId());
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, authenticationPath + "?username=" + username);
    }
}