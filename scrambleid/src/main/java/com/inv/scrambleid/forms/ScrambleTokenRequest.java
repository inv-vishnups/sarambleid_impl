package com.inv.scrambleid.forms;

public record ScrambleTokenRequest(
        String grant_type,
        String client_id,
        String client_secret
) {}
