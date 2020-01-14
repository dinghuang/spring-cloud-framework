package org.dinghuang.gateway.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Api(value = "swagger", tags = {"swagger"})
@RestController
@RequestMapping(value = "/v1/swagger")
public class SwaggerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerController.class);

    @PostMapping
    @ApiOperation(value = "注册swagger")
    public synchronized void registerSwagger(@ApiParam(value = "服务名称", required = true)
                                             @RequestParam String serviceName) {
        SwaggerRoutes swaggerRoutes = SwaggerRoutes.getSingleton();
        List<String> strings = swaggerRoutes.getNames();
        if (strings == null || strings.isEmpty()) {
            List<String> stringList = new ArrayList<>(1);
            stringList.add(serviceName);
            swaggerRoutes.setNames(stringList);
            LOGGER.info("add swagger routes:{}", serviceName);
        } else {
            if (!strings.contains(serviceName)) {
                strings.add(serviceName);
                swaggerRoutes.setNames(strings);
                LOGGER.info("add swagger routes:{}", serviceName);
            }
        }
    }
}
