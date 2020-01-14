package org.dinghuang.gateway.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Component
@Primary
public class DocumentationConfiguration implements SwaggerResourcesProvider {


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        SwaggerRoutes swaggerRoutes = SwaggerRoutes.getSingleton();
        List<String> names = new ArrayList<>();
        //todo 用消息队列监听实例启动的时候往这里注册服务
        names.add("demo-service");
        swaggerRoutes.setNames(names);
        if (swaggerRoutes.getNames() != null) {
            swaggerRoutes.getNames().forEach((k -> resources.add(swaggerResource(k, "/" + k + "/v2/api-docs", "2.0"))));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
