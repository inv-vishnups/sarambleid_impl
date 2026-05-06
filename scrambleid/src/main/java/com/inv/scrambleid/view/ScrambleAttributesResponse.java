package com.inv.scrambleid.view;

public record ScrambleAttributesResponse(
        Boolean desktopAppEnabled,
        Boolean mobileAppEnabled,
        String managerEmail
) {}
