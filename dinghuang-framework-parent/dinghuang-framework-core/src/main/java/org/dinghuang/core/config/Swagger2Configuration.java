package org.dinghuang.core.config;

import com.google.common.base.Predicates;
import org.dinghuang.core.properties.OAuth2ClientProperties;
import org.dinghuang.core.properties.OAuth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Configuration
@ConditionalOnProperty(prefix = "dinghuang.swagger", name = "enabled", havingValue = "true")
@EnableSwagger2
public class Swagger2Configuration {

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired(required = false)
    private OAuth2Properties oAuth2Properties;

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
                .paths(Predicates.not(PathSelectors.regex("/druids.*")))
                // 对根下所有路径进行监控
                .paths(PathSelectors.regex("/.*"))
                .build();
        if (oAuth2Properties != null) {
            docket.securitySchemes(Collections.singletonList(securityScheme()))
                    .securityContexts(Collections.singletonList(securityContext()));
        }
        return docket;
    }

    /**
     * 项目信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceName + " RESTful APIs")
                .description(serviceName + "系统接口文档说明")
                .contact(new Contact("dinghuang", "", "dinghuang123@gmail.com"))
                .version("1.0")
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "dinghuang.security.oauth2", name = "enabled", havingValue = "true")
    List<GrantType> grantTypes() {
        List<GrantType> grantTypes = new ArrayList<>();
        for (OAuth2ClientProperties config : oAuth2Properties.getClients()) {
            TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("http://localhost:9090/oauth/authorize", config.getClientId(), config.getClientSecret());
            TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost:9090/oauth/token", "access_token");
            grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
        }
        return grantTypes;
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "none", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("OAuth2")
                .grantTypes(grantTypes())
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    /**
     * 设置 swagger2 认证的安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("oauth2", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 设置认证的scope
     *
     * @return AuthorizationScope
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope!")
        };
    }

}
