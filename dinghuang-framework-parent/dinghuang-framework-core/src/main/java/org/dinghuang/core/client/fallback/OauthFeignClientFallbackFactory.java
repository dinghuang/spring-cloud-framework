package org.dinghuang.core.client.fallback;

import feign.hystrix.FallbackFactory;
import org.dinghuang.core.client.OauthFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@Component
public class OauthFeignClientFallbackFactory implements FallbackFactory<OauthFeignClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthFeignClientFallbackFactory.class);


    @Override
    public OauthFeignClient create(Throwable cause) {
        return null;
    }
}
