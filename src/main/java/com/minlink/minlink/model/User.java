package com.minlink.minlink.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    @Id
    private String id;

    private final String userId;
    private final String authProvider;

    public User(String userId,
                String authProvider) {
        this.userId = userId;
        this.authProvider = authProvider;
    }

    public String getuserId() {
        return userId;
    }

    public String getauthProvider() {
        return authProvider;
    }

}
