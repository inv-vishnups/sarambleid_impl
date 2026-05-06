package com.inv.scrambleid.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ScimUserRequest(

        List<String> schemas,
        String userName,
        NameRequest name,
        List<EmailRequest> emails,
        boolean active,
        List<RoleRequest> roles,

        @JsonProperty("urn:ietf:params:scim:schemas:extension:scrambleid:acme:2.0:User")
        ScrambleExtensionRequest extension,

        ScrambleOpsRequest scrambleOps
) {}
