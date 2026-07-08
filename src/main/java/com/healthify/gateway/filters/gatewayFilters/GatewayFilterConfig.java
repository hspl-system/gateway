package com.healthify.gateway.filters.gatewayFilters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayFilterConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(GatewayFilterConfig.class);

    @Bean
    public HandlerFilterFunction<ServerResponse, ServerResponse> loggingGatewayFilter() {

        return (request, next) -> {

            logger.info("===== Gateway BEFORE =====");
            logger.info("URI : {}", request.uri());

            ServerResponse response = next.handle(request);

            logger.info("===== Gateway AFTER =====");
            logger.info("Status : {}", response.statusCode());

            return response;
        };
    }
}