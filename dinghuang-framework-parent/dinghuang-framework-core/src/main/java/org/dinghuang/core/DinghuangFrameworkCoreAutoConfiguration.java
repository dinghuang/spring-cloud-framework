package org.dinghuang.core;

import org.dinghuang.core.config.ExceptionInterceptConfiguration;
import org.dinghuang.core.config.JsonDataConvertConfiguration;
import org.dinghuang.core.config.Swagger2Configuration;
import org.dinghuang.core.controller.DruidStatController;
import org.dinghuang.core.mybatis.config.CustomMetaObjectConfiguration;
import org.dinghuang.core.mybatis.config.MybatisPlusConfiguration;
import org.dinghuang.core.mybatis.config.SqlInjectorConfiguration;
import org.dinghuang.core.utils.SpringContextUtils;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * core包自动配置bean
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Configuration
@ConditionalOnWebApplication
@ImportAutoConfiguration(value = {
        Swagger2Configuration.class,
        ExceptionInterceptConfiguration.class,
        MybatisPlusConfiguration.class,
        SqlInjectorConfiguration.class,
        JsonDataConvertConfiguration.class,
        DruidStatController.class,
        CustomMetaObjectConfiguration.class})
public class DinghuangFrameworkCoreAutoConfiguration {

    @Bean
    public SpringContextUtils applicationContextHelper() {
        return new SpringContextUtils();
    }


}
