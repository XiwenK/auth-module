package com.manunin.auth.secutiry.exception;

import com.manunin.auth.exception.ErrorResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ErrorResponseHandler errorResponseHandler;

    @Autowired
    public RestAuthenticationFailureHandler(final ErrorResponseHandler errorResponseHandler) {
        this.errorResponseHandler = errorResponseHandler;
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException e) {
        errorResponseHandler.handle(e, response);
    }
}
