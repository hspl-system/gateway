package com.healthify.gateway.filters.gatewayFilters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class OpdServiceFilterConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(OpdServiceFilterConfig.class);

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

    @Bean
    public HandlerFilterFunction<ServerResponse, ServerResponse> securityHeaderFilter(
            OAuth2AuthorizedClientService authorizedClientService) {

        return (request, next) -> {

            logger.info("===== Adding JWT to forwarded request =====");

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
                logger.warn("No OAuth2 Authentication found.");
                return next.handle(request);
            }

            if (!(oauthToken.getPrincipal() instanceof OidcUser oidcUser)) {
                return next.handle(request);
            }

            String idToken = oidcUser.getIdToken().getTokenValue();

            logger.info("ID Token : {}", idToken);

            OidcIdToken token = oidcUser.getIdToken();

            System.out.println(token.getAccessTokenHash()+token.getTokenValue()+token.getAudience());
            System.out.println(token.getClaims());


            ServerRequest modifiedRequest = ServerRequest.from(request)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + idToken)
                    .build();

            return next.handle(modifiedRequest);
        };
    }
}