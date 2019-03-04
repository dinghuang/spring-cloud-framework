package org.dinghuang.websocket;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/28
 */
@Controller
public class WebsocketSourceConfiguration {
    @Bean
    ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
