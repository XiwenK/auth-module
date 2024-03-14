package com.manunin.score.configuration;

import com.manunin.score.secutiry.jwt.JwtAuthenticationProvider;
import com.manunin.score.secutiry.jwt.RefreshTokenAuthenticationProvider;
import com.manunin.score.secutiry.login.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class AuthenticationManagerConfiguration {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final LoginAuthenticationProvider loginAuthenticationProvider;

    private final RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider;

    public AuthenticationManagerConfiguration(final JwtAuthenticationProvider jwtAuthenticationProvider,
                                              final LoginAuthenticationProvider loginAuthenticationProvider,
                                              final RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.refreshTokenAuthenticationProvider = refreshTokenAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
        DefaultAuthenticationEventPublisher eventPublisher = objectPostProcessor
                .postProcess(new DefaultAuthenticationEventPublisher());
        var auth = new AuthenticationManagerBuilder(objectPostProcessor);
        auth.authenticationEventPublisher(eventPublisher);
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(refreshTokenAuthenticationProvider);
        return auth.build();
    }
}
