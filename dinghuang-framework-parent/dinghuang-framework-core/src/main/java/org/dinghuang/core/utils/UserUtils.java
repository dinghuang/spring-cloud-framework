package org.dinghuang.core.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.dinghuang.core.model.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/5/15
 */
public class UserUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    public static UserDO getUser() {
        UserDO user = new UserDO();
        if (RequestContextHolder.getRequestAttributes() == null) {
            user.setAccount("admin");
            user.setName("管理员");
            return user;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey("dinghuang".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }
        if (claims == null) {
            user.setAccount("admin");
            user.setName("管理员");
            return user;
        } else {
            return JSON.parseObject(JSON.toJSONString(claims.get("user")), UserDO.class);
        }
    }
}
