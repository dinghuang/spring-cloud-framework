package org.dinghuang.core.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dinghuang.core.security.exception.AuthenticationException;
import org.dinghuang.core.security.model.SecurityUser;
import org.dinghuang.core.utils.Base64Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Component
public class TokenUtils {

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    private static final String PATTERN = "yyyyMMddHHmmss";
    private static final String SING = ",";
    private static final String CREATED = "created";
    /**
     * 用户设备
     */
    private static final String AUDIENCE = "audience";
    private static final String USERNAME = "username";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 设置token秘钥
     *
     * @param secret secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 设置token到期时间
     *
     * @param expiration expiration
     */
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    /**
     * 生成AuthToken, 应用于手机端
     *
     * @param token  security token
     * @param userId 用户id
     * @return String
     */
    public String createAuthToken(String token, String userId) {
        String result;
        try {
            String dateText = DateFormatUtils.format(generateCurrentDate(), PATTERN);
            String text = dateText + SING + userId + SING + token;
            result = Base64Utils.encode(text);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * 时间,用户,token
     *
     * @param authToken authToken
     * @return String[]
     */
    public String[] decodeAuthToken(String authToken) {
        try {
            String result = Base64Utils.decode(authToken);
            if (result.length() > 72) {
                String time = result.substring(0, result.indexOf(SING));
                String subString = result.substring(result.indexOf(SING) + 1);
                String userId = subString.substring(0, subString.indexOf(SING));
                String token = subString.substring(subString.indexOf(SING) + 1);
                return new String[]{time, userId, token};
            } else {
                throw new AuthenticationException("Token length inconsistent");
            }
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to resolve token to time and user");
        }
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to from token get username");
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CREATED));
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to from token get created");
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to from token get expiration date");
        }
        return expiration;
    }

    private String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(AUDIENCE);
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to from token get audience");
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationException(" Failed to resolve token to claims");
        }
        return claims;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    /**
     * 校验token是否过期
     *
     * @param token token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(generateCurrentDate());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, userDetails.getUsername());
        claims.put(AUDIENCE, generateAudience(device));
        claims.put(CREATED, generateCurrentDate());
        return generateToken(claims);
    }

    /**
     * 创建token
     *
     * @param claims claims
     * @return String
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return (!(isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && (!(isTokenExpired(token)) || ignoreTokenExpiration(token)));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, generateCurrentDate());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return (username.equals(user.getUsername()) && !(isTokenExpired(token)) && !(isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())));
    }

}
