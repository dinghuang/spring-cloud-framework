package org.dinghuang.core.security.service;

import org.dinghuang.core.config.WebSecurityConfiguration;
import org.dinghuang.core.security.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * 无密码登录
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Service
public class SpecialUserService {
    private static Logger LOGGER = LoggerFactory.getLogger(SpecialUserService.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    public String login(String username, HttpServletResponse response) {
        return login(username, new Device() {

            @Override
            public boolean isNormal() {
                return true;
            }

            @Override
            public boolean isMobile() {
                return false;
            }

            @Override
            public boolean isTablet() {
                return false;
            }

            @Override
            public DevicePlatform getDevicePlatform() {
                return DevicePlatform.UNKNOWN;
            }
        }, response);
    }

    public String login(String username, Device device, HttpServletResponse response) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            String info = "user " + username + " no exist";
            LOGGER.error(info);
            throw new UsernameNotFoundException(info);
        }
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        WebSecurityConfiguration.getMD5("bfff4dadc47f424f90251c95817c938b")
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.info("user: {} login success, Device: {}, roles: {}",
                username,
                device,
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String token = tokenUtils.generateToken(userDetails, device);
        response.addHeader(tokenHeader, token);
        return token;
    }
}
