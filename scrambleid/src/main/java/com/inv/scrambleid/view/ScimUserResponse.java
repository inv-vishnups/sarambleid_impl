package com.inv.scrambleid.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ScimUserResponse(

        List<String> schemas,
        String id,
        String userName,
        NameResponse name,
        List<EmailResponse> emails,
        Boolean active,
        List<RoleResponse> roles,

        @JsonProperty("urn:ietf:params:scim:schemas:extension:scrambleid:acme:2.0:User")
        ScrambleExtensionResponse extension,

        MetaResponse meta
) {}
