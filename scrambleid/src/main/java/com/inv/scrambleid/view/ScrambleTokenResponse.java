package com.inv.scrambleid.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScrambleTokenResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Long expiresIn
) {}
