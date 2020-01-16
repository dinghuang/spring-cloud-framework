package org.dinghuang.demo.conf;

import org.dinghuang.core.adapter.UserInfoAdapter;
import org.dinghuang.core.model.UserDO;
import org.springframework.context.annotation.Configuration;

/**
 * @author dinghuang123@gmail.com
 * @since 2020/1/16
 */
@Configuration(value = "userInfoAdapter")
public class UserConfigure implements UserInfoAdapter {

    @Override
    public UserDO getUser() {
        //oauth2 获取用户的方式
//        UserDO user = new UserDO();
//        if (RequestContextHolder.getRequestAttributes() == null) {
//            user.setAccount("admin");
//            user.setName("管理员");
//            return user;
//        }
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "Bearer ");
//        Claims claims = null;
//        try {
//            claims = Jwts.parser().setSigningKey("dinghuang".getBytes("UTF-8")).parseClaimsJws(token).getBody();
//        } catch (UnsupportedEncodingException e) {
//            LOGGER.error(e.getMessage());
//        }
//        if (claims == null) {
//            user.setAccount("admin");
//            user.setName("管理员");
//            return user;
//        } else {
//            return JSON.parseObject(JSON.toJSONString(claims.get("user")), UserDO.class);
//        }
        UserDO user = new UserDO();
        user.setAccount("admin");
        user.setName("管理员");
        return user;
    }
}
