package com.minlink.minlink.model;

public record UrlResponse(
        String originalUrl,
        String shortUrl,
        boolean isDeactivated,
        int hitCount
) {

}
