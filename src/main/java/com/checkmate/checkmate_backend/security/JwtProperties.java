package com.checkmate.checkmate_backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "checkmate.jwt")
public class JwtProperties {

    /**
     * HMAC secret used to sign tokens.
     * In production, load this from a secure location (env, secret manager).
     */
    private String secret;

    /**
     * Token lifetime in seconds.
     */
    private long expirationSeconds;

    /**
     * Name of the HttpOnly cookie carrying the JWT.
     */
    private String cookieName;

    // Getters and setters

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
