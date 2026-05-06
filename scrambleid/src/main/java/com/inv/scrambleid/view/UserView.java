package com.inv.scrambleid.view;

import java.util.List;

public record UserView(
        Long id,
        String userName,
        NameView name,
        List<EmailView> emails,
        List<String> roles
) {}