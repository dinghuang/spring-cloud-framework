package org.dinghuang.feign;

import org.dinghuang.feign.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@FeignClient(name = "user-service", fallbackFactory = UserFeignClientFallback.class)
public interface UserFeignClient {

    /**
     * 锁定记录
     */
    @GetMapping(value = "/api/service/lock/{id}")
    String lock(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag);

    /**
     * 是否锁定检查
     */
    @GetMapping(value = "/api/service/lock/check/{id}")
    String lockCheck(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag);

    /**
     * 解锁记录
     */
    @GetMapping(value = "/api/service/unlock/{id}/{key}")
    String unlock(@PathVariable("id") String id, @PathVariable("key") String key, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag);

}
