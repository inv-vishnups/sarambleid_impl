package com.inv.scrambleid.forms;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email
) {}
