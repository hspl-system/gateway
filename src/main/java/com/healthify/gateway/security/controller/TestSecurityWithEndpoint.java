package com.healthify.gateway.security.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityWithEndpoint {

    @GetMapping("/token")
    public String token(
            OAuth2AuthenticationToken auth,
            @RegisteredOAuth2AuthorizedClient("google")
            OAuth2AuthorizedClient client) {

        return client.getAccessToken().getTokenValue();
    }
}
