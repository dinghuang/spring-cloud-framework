package org.dinghuang.oauth.controller;

import org.dinghuang.oauth.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Controller
public class OauthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

    @Autowired
    private OAuth2Properties oAuth2Properties;

//    @GetMapping("/user")
//    public Object getUser(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
//        LOGGER.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JSON.toJSON(authentication));
//
//        String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "bearer ");
//
//        Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
//        String userStr = (String) claims.get("user");
//        UserDO userDO = JSON.parseObject(userStr, UserDO.class);
//        LOGGER.info("【SecurityOauth2Application】 getUser {}", userDO.toString());
//        return authentication;
//    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/401")
    public String accessDenied() {
        return "401";
    }

    @GetMapping("/user/common")
    public String common() {
        return "user/common";
    }

    @GetMapping("/user/admin")
    public String admin() {
        return "user/admin";
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
