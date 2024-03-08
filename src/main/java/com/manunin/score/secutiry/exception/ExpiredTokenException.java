package com.manunin.score.secutiry.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {
    private String token;

    public ExpiredTokenException(String token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }
}
