//package org.dinghuang.core.security;
//
//import com.alibaba.fastjson.JSON;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.apache.commons.lang.StringUtils;
//import org.dinghuang.core.client.OauthFeignClient;
//import org.dinghuang.core.model.UserDO;
//import org.dinghuang.core.utils.SpringContextUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author dinghuang123@gmail.com
// * @since 2019/6/9
// */
//public class JwtTokenFilter implements Filter {
//
//    private ResourceServerTokenServices tokenServices;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
//
//    public JwtTokenFilter(ResourceServerTokenServices tokenServices) {
//        this.tokenServices = tokenServices;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
//                filterConfig.getServletContext());
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        try {
//            String header = httpRequest.getHeader("Authorization");
//            String token = StringUtils.substringAfter(header, "Bearer ");
//            if (token == null) {
//                LOGGER.debug("No Jwt token in request, will continue chain.");
//                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Jwt token in request.");
//                return;
//            }
//            Claims claims = Jwts.parser().setSigningKey("dinghuang".getBytes("UTF-8")).parseClaimsJws(token).getBody();
//            if (claims == null) {
//                LOGGER.debug("No Jwt token in request, will continue chain.");
//                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Jwt token in request.");
//                return;
//            }
//            UserDO userDO = JSON.parseObject(JSON.toJSONString(claims.get("user")), UserDO.class);
//            if (userDO == null) {
//                LOGGER.debug("No Jwt token in request, will continue chain.");
//                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Jwt token in request.");
//                return;
//            } else {
//                //todo 对token进行校验
//                OauthFeignClient oauthFeignClient = (OauthFeignClient) SpringContextUtils.getContext().getBean("oauthFeignClient");
//                oauthFeignClient.validatorToken();
//            }
//            chain.doFilter(request, response);
//        } catch (OAuth2Exception e) {
//            SecurityContextHolder.clearContext();
//            LOGGER.debug("Authentication request failed: ", e);
//            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
//        }catch (ExpiredJwtException expiredJwtException){
//            SecurityContextHolder.clearContext();
//            LOGGER.debug("Authentication request failed: ", expiredJwtException);
//            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token.");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // Do nothing
//    }
//
//}
