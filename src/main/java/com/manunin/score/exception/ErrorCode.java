package com.manunin.score.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GENERAL(1),
    AUTHENTICATION(2),
    JWT_TOKEN_EXPIRED(3);


    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
