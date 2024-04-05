package com.manunin.score.secutiry.login;

import com.manunin.score.model.User;
import com.manunin.score.service.UserDetailsImpl;
import com.manunin.score.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder encoder;

    @Autowired
    public LoginAuthenticationProvider(final UserService userService) {
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = authentication.getCredentials().toString();

        UserDetails securityUser = authenticateByUsernameAndPassword(authentication, username, password);

        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }

    private UserDetails authenticateByUsernameAndPassword(final Authentication authentication,
                                                          final String username,
                                                          final String password) {
        if (!userService.existsByUsername(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        User user = userService.findByUsername(username);

        if (!encoder.matches(password, user.getPassword())) {
            throw new InsufficientAuthenticationException("Invalid password");
        }

        return UserDetailsImpl.build(user);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
