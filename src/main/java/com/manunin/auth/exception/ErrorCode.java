package com.manunin.auth.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GENERAL(1, HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION(2, HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_EXPIRED(3, HttpStatus.UNAUTHORIZED),
    BAD_REQUEST_PARAMS(10, HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(20, HttpStatus.FORBIDDEN);

    private final int code;
    private final HttpStatus status;

    ErrorCode(final int code, final HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
