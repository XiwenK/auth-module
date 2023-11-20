package com.manunin.score.secutiry.jwt;

import com.manunin.score.exception.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.manunin.score.exception.AuthErrorType.INVALID_CREDENTIALS;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        Throwable cause = authException.getCause();
        if (cause instanceof SecurityException) {
            setErrorResponse(response, (SecurityException) cause);
            return;
        }
        setErrorResponse(response, new SecurityException(INVALID_CREDENTIALS));
    }

    private static void setErrorResponse(HttpServletResponse response, SecurityException securityException) throws IOException {
        logger.error("Security error: {}", securityException.getMessage());
        response.setStatus(securityException.getType().getStatus());
        response.getWriter().write(securityException.getMessage());
    }
}