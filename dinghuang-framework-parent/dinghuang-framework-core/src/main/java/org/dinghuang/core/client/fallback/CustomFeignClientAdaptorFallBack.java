package org.dinghuang.core.client.fallback;

import feign.hystrix.FallbackFactory;
import org.dinghuang.core.client.CustomFeignClientAdaptor;
import org.dinghuang.core.exception.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/15
 */
public class CustomFeignClientAdaptorFallBack implements FallbackFactory<CustomFeignClientAdaptor> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFeignClientAdaptorFallBack.class);


    @Override
    public CustomFeignClientAdaptor create(Throwable cause) {
        throw new FeignException("服务调用失败", cause);
    }
}