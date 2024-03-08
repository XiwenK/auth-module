package com.manunin.score.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public class JwtPair {

    @NotBlank
    @Schema(example = "token", description = "Access token")
    private String token;
    @NotBlank
    @Schema(example = "refreshToken", description = "Refresh")
    private String refreshToken;

    public JwtPair(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
