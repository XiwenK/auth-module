package com.manunin.score.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public enum AuthErrorType {
    INVALID_CREDENTIALS(HttpServletResponse.SC_UNAUTHORIZED, "exceptions.invalidCredentials"),
    TOO_MANY_LOGIN_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS.value(), "exceptions.tooManyLoginAttempts");

    private final int status;
    private final String message;

    AuthErrorType(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
