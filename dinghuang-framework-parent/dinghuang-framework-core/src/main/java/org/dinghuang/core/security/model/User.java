package org.dinghuang.core.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dinghuang.core.mybatis.model.BaseModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@TableName("user")
@Data
public class User extends BaseModel {

    protected String username;
    protected String realName;
    protected Long userId;
    protected String password;
    protected Integer passwordAttempt;
    protected String description;
    protected String email;
    protected Date lastPasswordReset;
    protected Date lastLoginAt;
    protected String authorities;

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getAuthorities() {
        if (StringUtils.isBlank(authorities)) {
            return "NONE";
        }
        return authorities;
    }
}
