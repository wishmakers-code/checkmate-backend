package com.checkmate.checkmate_backend.modules.user.dto;

public class AuthResponse {

    private Long userId;
    private String email;
    private String fullName;

    public AuthResponse(Long userId, String email, String fullName) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
