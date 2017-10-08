package com.avoinovan.drawlot.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author by avoinovan
 */
@Document(collection = "tokens")
public class AccessToken extends  AbstractEntity {

    private Platform platform;

    private String token;

    private String userId;

    public AccessToken(final Platform platform, final String token, final String userId) {
        this.platform = platform;
        this.token = token;
        this.userId = userId;
    }

    public AccessToken() {
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(final Platform platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
