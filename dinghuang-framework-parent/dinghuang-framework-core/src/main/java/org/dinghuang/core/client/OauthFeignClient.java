package org.dinghuang.core.client;

import org.dinghuang.core.client.fallback.OauthFeignClientFallbackFactory;
import org.dinghuang.core.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/7
 */
@FeignClient(name = "oauth-service", qualifier = "oauthFeignClient", fallbackFactory = OauthFeignClientFallbackFactory.class, configuration = FeignConfiguration.class)
public interface OauthFeignClient {

    @GetMapping("/validator_token")
    Boolean validatorToken();

}
