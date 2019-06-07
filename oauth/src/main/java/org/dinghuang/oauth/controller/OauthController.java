package org.dinghuang.oauth.controller;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Controller
public class OauthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @GetMapping("/user")
    public Object getUser(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        LOGGER.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JSON.toJSON(authentication));

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String userStr = (String) claims.get("user");
        UserDO userDO = JSON.parseObject(userStr, UserDO.class);
        LOGGER.info("【SecurityOauth2Application】 getUser {}", userDO.toString());

        return authentication;
    }

    @GetMapping("/forbidden")
    public String getForbidden() {
        return "forbidden";
    }

    @GetMapping("/permitAll")
    public String getPermitAll() {
        return "permitAll";
    }
}
