package com.crland.safe.gateway.config;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/22
 */
public class SwaggerRoutes {

    private volatile static SwaggerRoutes swaggerRoutes;

    private SwaggerRoutes() {
    }

    public static SwaggerRoutes getSingleton() {
        if (swaggerRoutes == null) {
            synchronized (SwaggerRoutes.class) {
                if (swaggerRoutes == null) {
                    swaggerRoutes = new SwaggerRoutes();
                }
            }
        }
        return swaggerRoutes;
    }

    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
