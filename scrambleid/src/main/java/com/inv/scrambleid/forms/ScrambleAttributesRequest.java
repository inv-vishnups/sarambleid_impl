package com.inv.scrambleid.forms;

public record ScrambleAttributesRequest(Boolean desktopAppEnabled,
                                        Boolean mobileAppEnabled,
                                        String managerEmail) {
}
