package org.dinghuang.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Controller
public class OauthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

    private ResourceServerTokenServices tokenServices;

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

    @GetMapping("/validator_token")
    public ResponseEntity<Boolean> validatorToken(@RequestParam(name = "token") String token) {
        Boolean condition = true;
        try {
            tokenServices.readAccessToken(token);
        } catch (Exception e) {
            condition = false;
        }
        return new ResponseEntity<>(condition, HttpStatus.OK);
    }

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

}
