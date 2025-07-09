package com.manunin.auth.exception;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.manunin.auth.secutiry.exception.ExpiredTokenException;
import com.manunin.auth.utils.JsonUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Exception Handler - Exception Classification:
 * 1. AuthenticationException
 * (1). ExpiredTokenException (self-defined)
 * (2). BadCredentialsException || UsernameNotFoundException (spring security)
 * (3). Other Auth Failure Exception (self-defined)
 * 2. AccessDeniedException (spring security)
 * 3. ServiceException (self-defined)
 * 4. Other Exception
 */
@RestControllerAdvice
public class ErrorResponseHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseHandler.class);

    /**
     * Handle exceptions and encapsulate them into ErrorResponse
     * 
     * Use JsonUtils to write the object ErrorResponse as json data
     * into HttpServletResponse
     * 
     * @param exception
     * @param response
     */
    @ExceptionHandler(Exception.class)
    public void handle(final Exception exception, final HttpServletResponse response) {
        logger.debug("Processing exception {}", exception.getMessage(), exception);

        // If response is already commited to frontend
        if (response.isCommitted()) {
            return;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        switch (exception) {
            case AuthenticationException authenticationException ->
                handleAuthenticationException(authenticationException, response);
            case ServiceException serviceException -> handleServiceException(serviceException, response);
            default -> handleInternalServerError(exception, response);
        }
    }

    private static void handleInternalServerError(Exception exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        JsonUtils.writeValue(getWriter(response), ErrorResponse.of(exception.getMessage(), ErrorCode.GENERAL));
    }

    private static void handleServiceException(ServiceException exception, HttpServletResponse response) {
        ErrorCode errorCode = exception.getErrorCode();
        response.setStatus(errorCode.getStatus().value());
        JsonUtils.writeValue(getWriter(response), ErrorResponse.of(exception.getMessage(), errorCode));
    }

    private static PrintWriter getWriter(HttpServletResponse response) {
        try {
            return response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleAuthenticationException(final AuthenticationException authenticationException,
            final HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (authenticationException instanceof ExpiredTokenException) {
            JsonUtils.writeValue(getWriter(response),
                    ErrorResponse.of("exception.tokenExpired", ErrorCode.JWT_TOKEN_EXPIRED));
        } else if (authenticationException instanceof BadCredentialsException
                || authenticationException instanceof UsernameNotFoundException) {
            JsonUtils.writeValue(getWriter(response),
                    ErrorResponse.of("exception.badCredentials", ErrorCode.AUTHENTICATION));
        } else {
            JsonUtils.writeValue(getWriter(response),
                    ErrorResponse.of("exception.authenticationFailed", ErrorCode.AUTHENTICATION));
        }
    }

    @Override
    public void handle(final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException accessDeniedException) {
        if (!response.isCommitted()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonUtils.writeValue(getWriter(response),
                    ErrorResponse.of("exception.accessDenied", ErrorCode.ACCESS_DENIED));
        }
    }
}
