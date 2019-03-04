package org.dinghuang.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "dinghuang.swagger", value = "enabled")
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.dinghuang.application.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 项目信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("dinghuang project RESTful APIs")
                .description("作者联系方式：微信：742041978")
                .version("1.0")
                .build();
    }
}
