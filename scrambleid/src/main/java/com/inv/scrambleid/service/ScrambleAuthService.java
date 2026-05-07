package com.inv.scrambleid.service;

import com.inv.scrambleid.view.IntrospectionResponse;
import com.inv.scrambleid.view.TokenResponse;
import reactor.core.publisher.Mono;

public interface ScrambleAuthService {

    String getAccessToken();

    Mono<TokenResponse> getAccessTokenByAuthorizationCode(String authorizationCode);

    public Mono<IntrospectionResponse> introspect(String token);
}