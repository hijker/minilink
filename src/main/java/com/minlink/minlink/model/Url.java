package com.minlink.minlink.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Url {

    @Id
    String id;
    private String userId;
    private String originalUrl;
    private String shortUrl;
    private boolean isDeactivated;
    private int hitCount;

    public Url() {
    }

    public Url(String userId, String originalUrl, String shortUrl) {
        this.userId = userId;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.isDeactivated = false;
        this.hitCount = 0;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }

    public UrlResponse getUrlResponse() {
        return new UrlResponse(this.originalUrl, this.shortUrl, this.isDeactivated, this.hitCount);
    }
}
