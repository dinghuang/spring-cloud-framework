package org.dinghuang.security.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private String captcha;
    private String captchaCode;

    private static final String CAPTCHA_CODE = "captchaCode";
    private static final String CAPT = "captcha";

    /**
     * 构造方法
     *
     * @param request 会提取captcha
     */
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        HttpSession session = request.getSession();
        captchaCode = (String) session.getAttribute(CAPTCHA_CODE);
        captcha = request.getParameter(CAPT);
    }
}