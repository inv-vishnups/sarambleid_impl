package com.inv.scrambleid.service.impl;


import com.inv.scrambleid.forms.ScrambleTokenRequest;
import com.inv.scrambleid.service.ScrambleAuthService;
import com.inv.scrambleid.view.IntrospectionResponse;
import com.inv.scrambleid.view.ScrambleTokenResponse;
import com.inv.scrambleid.view.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScrambleAuthServiceImpl implements ScrambleAuthService {

    private final WebClient webClient;

    @Value("${scramble.base-url}")
    private String baseUrl;

    @Value("${scramble.client-id}")
    private String clientId;

    @Value("${scramble.client-secret}")
    private String clientSecret;

    @Override
    public String getAccessToken() {

        ScrambleTokenRequest request = new ScrambleTokenRequest(
                "client_credentials",
                "ffbccdf8-8479-4ebd-8af1-78b4eb88f545",
                "4c844db4ccdf0b429e3e7e982558c7dd5c3c9da5f3b8c8579ffbc7022feeee84"
        );

        ScrambleTokenResponse response = webClient.post()
                .uri(baseUrl + "/api/v1/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ScrambleTokenResponse.class)
                .block();

        if (response == null || response.accessToken() == null) {
            throw new RuntimeException("Failed to fetch Scramble access token");
        }

        return response.accessToken();
    }

    @Override
    public Mono<TokenResponse> getAccessTokenByAuthorizationCode(String authorizationCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", authorizationCode);
        formData.add("redirect_uri", "http://localhost:4200/callback");
        formData.add("client_id", "e500698f-2202-4151-bc1c-d5ac784c6a71");
        formData.add("client_secret", "82cdef5fd0ee9317e7eb8fdab09f359986219db6cdd155688fce287d2f03e743");

        return webClient.post()
                .uri("https://prod.scrambleid.com/oidc/inno/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> {
                                    System.out.println("TOKEN ERROR => " + error);
                                    return Mono.error(new RuntimeException(error));
                                })
                )
                .bodyToMono(TokenResponse.class);
    }

    public Mono<IntrospectionResponse> introspect(String token) {


        String client_id= "01485052-e818-443d-bc3a-0c993f94d89d";
        String client_secret="ad83c22a916ff24bc70cb08b1bf614549c3c52a92b5ed3fc452c09ae74b8bb6c";

        String credentials = client_id + ":" + client_secret;

        String basicAuth = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        return webClient.post()
                .uri("https://prod.scrambleid.com/api/v1/oauth/introspect")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                // IMPORTANT:
                // DO NOT SET AUTHORIZATION HEADER

                .bodyValue(Map.of(
                        "token", token
                ))

                .retrieve().onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(error ->
                                        Mono.error(new RuntimeException(error)))
                ).bodyToMono(IntrospectionResponse.class);
    }
}

