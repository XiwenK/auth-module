package com.manunin.score.secutiry.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {
    private String token;

    public ExpiredTokenException(final String token, final String msg, final Throwable t) {
        super(msg, t);
        this.token = token;
    }
}
