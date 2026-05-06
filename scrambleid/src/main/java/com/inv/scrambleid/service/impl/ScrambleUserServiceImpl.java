package com.inv.scrambleid.service.impl;

import com.inv.scrambleid.forms.*;
import com.inv.scrambleid.service.ScrambleAuthService;
import com.inv.scrambleid.service.ScrambleUserService;
import com.inv.scrambleid.view.ScimUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrambleUserServiceImpl implements ScrambleUserService {

    private final WebClient webClient;
    private final ScrambleAuthService scrambleAuthService;

    @Value("${scramble.base-url}")
    private String baseUrl;

    @Override
    public String createUser(RegisterRequest request) {

        String accessToken = scrambleAuthService.getAccessToken();

        System.out.println(accessToken);

        ScimUserRequest scimRequest = new ScimUserRequest(
                List.of(
                        "urn:ietf:params:scim:schemas:core:2.0:User",
                        "urn:ietf:params:scim:schemas:extension:scrambleid:inno:2.0:User"
                ),
                request.firstName()+" "+request.lastName(),
                new NameRequest(request.firstName(), request.lastName()),
                List.of(new EmailRequest(request.email(), "work", true)),
                true,
                List.of(new RoleRequest("USER")),
                new ScrambleExtensionRequest(
                        new ScrambleAttributesRequest(
                                true,
                                true,
                                "abdul.fahad@innovaturelabs.com" // you can make dynamic
                        )
                ),
                new ScrambleOpsRequest(true, true)
        );

        // 🚀 Call SCIM API

        ScimUserResponse response = webClient.post()
                .uri(baseUrl + "/api/v1/scim/v2/Users")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.valueOf("application/scim+json"))
                .accept(MediaType.valueOf("application/scim+json"))
                .bodyValue(scimRequest)
                .retrieve()
                .onStatus(status -> status.value() == 409,
                        responses -> responses.bodyToMono(String.class)
                                .map(body -> new RuntimeException("User already exists in Scramble: " + body))
                )
                .bodyToMono(ScimUserResponse.class)
                .block();

        if (response == null || response.id() == null) {
            throw new RuntimeException("Failed to create user in Scramble");
        }

        System.out.println("Scramble user created with ID: "+ response.id());

        // ✅ Extract ID
        return response.id();
    }
}
