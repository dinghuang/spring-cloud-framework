package org.dinghuang.oauth.handler;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义会话过期策略
 *
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class CustomerExpiredSessionHandler implements SessionInformationExpiredStrategy {

    /**
     * 检测到会话过期
     *
     * @param sessionInformationExpiredEvent sessionInformationExpiredEvent
     * @throws IOException IOException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        //todo 会话过期策略
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=UTF-8");
        sessionInformationExpiredEvent.getResponse().getWriter().write("并发登录!");
    }
}
