package com.inv.scrambleid.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IntrospectionResponse {

    private boolean active;

    @JsonProperty("client_id")
    private String clientId;

    private String sub;

    private String scope;

    private String iss;

    private String gty;

    @JsonProperty("token_class")
    private String tokenClass;

    @JsonProperty("grant_type")
    private String grantType;

    private Long exp;

    private String jti;
}
