package com.inv.scrambleid.view;

public record MetaResponse(
        String resourceType,
        String location,
        String created,
        String lastModified
) {}
