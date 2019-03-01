package org.dinghuang.core.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.dinghuang.core.security.enums.UserEnum;
import org.dinghuang.core.security.model.User;
import org.dinghuang.core.security.repository.UserRepository;
import org.dinghuang.core.security.service.LoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Component
@Data
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRecord loginRecord;

    @Value("${jwt.route.authentication.redirect.url:/}")
    private String defaultUrl;

    @Value("${jwt.route.authentication.ssl::false}")
    private boolean useSSL;

    @PostConstruct
    private void init() {
        this.setDefaultTargetUrl(defaultUrl);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = request.getParameter("username");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(UserEnum.USERNAME.value(), username);
        User user = userRepository.selectOne(userQueryWrapper);
        user.setLastLoginAt(new Date());
        user.setPasswordAttempt(0);
        userRepository.updateById(user);
        loginRecord.loginSuccess(user.getUserId());
        DefaultSavedRequest saveRequest = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            saveRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        }
        try {
            if (saveRequest == null) {
                super.onAuthenticationSuccess(request, response, authentication);
            }
            if (useSSL) {
                Field schemeField = DefaultSavedRequest.class.getDeclaredField("scheme");
                schemeField.setAccessible(true);
                schemeField.set(saveRequest, "https");
                Field portStringField = DefaultSavedRequest.class.getDeclaredField("serverPort");
                portStringField.setAccessible(true);
                portStringField.set(saveRequest, 443);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}