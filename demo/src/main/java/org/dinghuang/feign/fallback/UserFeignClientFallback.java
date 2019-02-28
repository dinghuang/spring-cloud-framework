package org.dinghuang.feign.fallback;

import feign.hystrix.FallbackFactory;
import org.dinghuang.core.exception.FeignException;
import org.dinghuang.feign.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Component
public class UserFeignClientFallback implements FallbackFactory<UserFeignClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFeignClientFallback.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public String lock(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag) {
                throw new FeignException("服务调用失败, 服务: UserSvc, 方法: lock, 参数: {}, 错误信息：{}", throwable.getMessage());
            }

            @Override
            public String lockCheck(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag) {
                throw new FeignException("服务调用失败, 服务: UserSvc, 方法: lockCheck, 参数: {}, 错误信息：{}", throwable.getMessage());

            }

            @Override
            public String unlock(@PathVariable("id") String id, @PathVariable("key") String key, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag) {
                throw new FeignException("服务调用失败, 服务: UserSvc, 方法: unlock, 参数: {}, 错误信息：{}", throwable.getMessage());

            }
        };
    }
}