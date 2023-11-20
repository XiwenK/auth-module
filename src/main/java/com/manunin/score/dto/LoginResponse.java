package com.manunin.score.dto;

import java.util.List;

public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private String type = "Bearer";
    private List<String> roles;

    public LoginResponse(Long id, String username, String email, String accessToken, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = accessToken;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
