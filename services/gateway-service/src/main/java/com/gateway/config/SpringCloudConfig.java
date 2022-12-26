package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Muhil
 *
 */
@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    	System.out.println("Routes initialized!");
        return builder.routes()
                .route(r -> r.path("/tm/**")
                        .uri("http://localhost:6060/")
                        .id("tenant-management"))
                .build();
    }

}