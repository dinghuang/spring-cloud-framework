package org.dinghuang.core.security;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.springframework.security.core.context.SecurityContext;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/6/10
 */
public class SecurityContextHystrixRequestVariable {

    private static final HystrixRequestVariableDefault<SecurityContext> securityContextVariable =
            new HystrixRequestVariableDefault<>();

    private SecurityContextHystrixRequestVariable() {
    }

    public static HystrixRequestVariableDefault<SecurityContext> getInstance() {
        return securityContextVariable;
    }

}
