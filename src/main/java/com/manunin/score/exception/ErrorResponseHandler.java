package com.manunin.score.exception;

import com.manunin.score.secutiry.exception.ExpiredTokenException;
import com.manunin.score.utils.JsonUtils;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ErrorResponseHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseHandler.class);

    @ExceptionHandler(Exception.class)
    public void handle(Exception exception, HttpServletResponse response) {
        logger.debug("Processing exception {}", exception.getMessage(), exception);
        if (!response.isCommitted()) {
            try {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                if (exception instanceof AuthenticationException) {
                    handleAuthenticationException((AuthenticationException) exception, response);
                } else if (exception instanceof ServiceException) {
                    ServiceException serviceException = (ServiceException) exception;
                    ErrorCode errorCode = serviceException.getErrorCode();
                    response.setStatus(errorCode.getStatus().value());
                    JsonUtils.writeValue(response.getWriter(), ErrorResponse.of(serviceException.getMessage(), errorCode));
                } else {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    JsonUtils.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage(), ErrorCode.GENERAL));
                }
            } catch (IOException e) {
                logger.error("Can't handle exception", e);
            }
        }
    }

    private void handleAuthenticationException(AuthenticationException authenticationException, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (authenticationException instanceof ExpiredTokenException) {
            JsonUtils.writeValue(response.getWriter(),
                    ErrorResponse.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED));
        }
        if (authenticationException instanceof BadCredentialsException || authenticationException instanceof UsernameNotFoundException) {
            JsonUtils.writeValue(response.getWriter(),
                    ErrorResponse.of("exception.badCredentials", ErrorCode.AUTHENTICATION));
        } else {
            JsonUtils.writeValue(response.getWriter(),
                    ErrorResponse.of("exception.authenticationFailed", ErrorCode.AUTHENTICATION));
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonUtils.writeValue(response.getWriter(), ErrorResponse.of("exception.accessDenied", ErrorCode.ACCESS_DENIED));
        }
    }
}
