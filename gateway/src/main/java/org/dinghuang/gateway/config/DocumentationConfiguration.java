package com.crland.safe.gateway.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/22
 */
@Component
@Primary
public class DocumentationConfiguration implements SwaggerResourcesProvider {


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        SwaggerRoutes swaggerRoutes = SwaggerRoutes.getSingleton();
        List<String> names = new ArrayList<>();
        names.add("sale-customer-service");
        names.add("sale-esb-service");
        names.add("sale-sale-service");
        names.add("sale-finance-service");
        names.add("sale-housing-service");
        names.add("sale-common-service");
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
