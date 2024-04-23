package com.manunin.auth.secutiry.jwt;

import com.manunin.auth.exception.ServiceException;
import com.manunin.auth.service.UserDetailsImpl;
import com.manunin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public RefreshTokenAuthenticationProvider(final UserService userService,
                                              final JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = tokenProvider.getUserNameFromJwtToken(token);
        UserDetailsImpl user;
        try {
            user = UserDetailsImpl.build(userService.findByUsername(username));
        } catch (ServiceException e) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (RefreshJwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
