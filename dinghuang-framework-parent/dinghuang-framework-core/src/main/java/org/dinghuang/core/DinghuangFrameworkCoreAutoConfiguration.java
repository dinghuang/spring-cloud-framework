package org.dinghuang.core;

import org.dinghuang.core.utils.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * core包自动配置bean
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Configuration
@MapperScan("org.dinghuang.infra.repository.*")
public class DinghuangFrameworkCoreAutoConfiguration {

    @Bean
    public SpringContextUtils applicationContextHelper() {
        return new SpringContextUtils();
    }


}
