package org.dinghuang.core.client;

import feign.RequestLine;
import org.dinghuang.core.client.fallback.CustomFeignClientAdaptorFallBack;
import org.dinghuang.core.config.CustomFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.net.URI;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/15
 */
@FeignClient(name = "customFeignClient", qualifier = "customFeignClient", fallback = CustomFeignClientAdaptorFallBack.class, configuration = CustomFeignConfiguration.class)
public interface CustomFeignClientAdaptor {

    @RequestLine("GET")
    ResponseEntity queryObjectById(URI baseUri);

}

