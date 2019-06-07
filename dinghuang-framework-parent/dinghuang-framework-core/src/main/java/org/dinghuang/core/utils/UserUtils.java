package org.dinghuang.core.utils;

import org.dinghuang.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/5/15
 */
public class UserUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    public static User getUser() {
        User user = new User();
        if (RequestContextHolder.getRequestAttributes() == null) {
            user.setAccount("admin");
            user.setName("管理员");
            return user;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String account = request.getHeader("account");
        String name = request.getHeader("name");
        user.setAccount(account == null ? "admin" : account);
        try {
            user.setName(name == null ? "管理员" : URLDecoder.decode(name, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn(e.getMessage());
        }
        return user;
    }
}
