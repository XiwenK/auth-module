package com.manunin.score.secutiry.jwt;

import com.manunin.score.dto.RefreshTokenRequest;
import com.manunin.score.secutiry.exception.AuthMethodNotSupportedException;
import com.manunin.score.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtTokenProvider tokenProvider;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    public RefreshTokenAuthenticationFilter(final JwtTokenProvider tokenProvider,
                                            final String url,
                                            final AuthenticationSuccessHandler successHandler,
                                            final AuthenticationFailureHandler failureHandler) {
        super(url);
        this.tokenProvider = tokenProvider;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if(logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        RefreshTokenRequest refreshTokenRequest;
        try {
            refreshTokenRequest = JsonUtils.fromReader(request.getReader(), RefreshTokenRequest.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Invalid login request payload");
        }

        if (StringUtils.isBlank(refreshTokenRequest.getRefreshToken())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        return getAuthenticationManager().authenticate(new RefreshJwtAuthenticationToken(refreshTokenRequest.getRefreshToken()));
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}

