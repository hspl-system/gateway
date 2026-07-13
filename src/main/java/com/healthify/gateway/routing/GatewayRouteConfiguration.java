package com.healthify.gateway.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;


import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayRouteConfiguration {

    @Bean
    RouterFunction<ServerResponse> opdServiceRoutes(
            HandlerFilterFunction<ServerResponse, ServerResponse> loggingGatewayFilter,  HandlerFilterFunction<ServerResponse, ServerResponse> securityHeaderFilter) {

        return route("opd-service") // routing builder : collects all routes
                .route(path("/hello"),
                        http("http://localhost:8080"))
                .route(path("/consultation/**"),
                        http("http://localhost:8080"))
                .filter(loggingGatewayFilter) // filter is global to routing builder and applies to all routes under builder
                .filter(securityHeaderFilter) //this filter adds the jwt token to header
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> w3erviceRouter(){
        return route("w3-school")
                .route(path("/java/**"),http("https://www.w3schools.com/")).build();
    }

}
