package org.dinghuang.core.security.controller;

import io.swagger.annotations.ApiOperation;
import org.dinghuang.core.security.exception.AuthenticationException;
import org.dinghuang.core.security.model.AuthenticationRequest;
import org.dinghuang.core.security.model.SecurityUser;
import org.dinghuang.core.security.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Controller
@RequestMapping(value = "/v1/authentication")
public class AuthenticationRestController {
    private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @ApiOperation(value = "根据用户名密码获取TOKEN", httpMethod = "POST")
    @PostMapping(value = "/${jwt.route.authentication.path}")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, Device device) {
        try {
            LOGGER.info("user starts logging in, username: {}, password: {}", authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("user: {} login success, Device: {}, role: {}",
                    authenticationRequest.getUsername(),
                    device,
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            // 重新加载安全后的密码，这样我们就可以生成令牌
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = tokenUtils.generateToken(userDetails, device);
            response.addHeader(tokenHeader, token);
        } catch (Exception e) {
            new ResponseEntity<>(new AuthenticationException("Incorrect user name or password"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "刷新TOKEN", httpMethod = "GET")
    @GetMapping(value = "/${jwt.route.authentication.refresh}")
    public ResponseEntity<String> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = tokenUtils.getUsernameFromToken(token);
        if (username != null) {
            SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(username);
            if (user != null) {
                if (tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
                    return new ResponseEntity<>(tokenUtils.refreshToken(token), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("TOKEN refresh fails", HttpStatus.FORBIDDEN);
    }

}