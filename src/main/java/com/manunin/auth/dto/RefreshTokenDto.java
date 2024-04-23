package com.manunin.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public class RefreshTokenDto {

    @NotBlank
    @Schema(example = "refreshToken", description = "Refresh token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }
}
