package org.dinghuang.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/validator_token")
    public ResponseEntity<Boolean> validatorToken() {
        return new ResponseEntity<>(true, HttpStatus.OK);
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
