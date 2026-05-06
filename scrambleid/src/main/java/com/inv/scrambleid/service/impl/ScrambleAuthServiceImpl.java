package com.inv.scrambleid.service.impl;


import com.inv.scrambleid.forms.ScrambleTokenRequest;
import com.inv.scrambleid.service.ScrambleAuthService;
import com.inv.scrambleid.view.ScrambleTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                clientId,
                clientSecret
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
}
