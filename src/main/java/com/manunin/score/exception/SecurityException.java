package com.manunin.score.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {
    private final AuthErrorType type;

    public SecurityException(final AuthErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public AuthErrorType getType() {
        return type;
    }
}

