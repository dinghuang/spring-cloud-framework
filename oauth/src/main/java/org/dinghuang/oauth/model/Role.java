package org.dinghuang.oauth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/8
 */
@Data
public class Role implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
