package org.dinghuang.core.security.model;

import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
