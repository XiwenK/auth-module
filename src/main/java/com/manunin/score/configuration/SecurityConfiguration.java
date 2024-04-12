package com.manunin.score.configuration;

import com.manunin.score.secutiry.jwt.AuthEntryPointJwt;
import com.manunin.score.secutiry.jwt.JwtTokenProvider;
import com.manunin.score.secutiry.jwt.RefreshTokenAuthenticationFilter;
import com.manunin.score.secutiry.jwt.TokenAuthenticationFilter;
import com.manunin.score.secutiry.login.LoginAuthenticationFilter;
import com.manunin.score.secutiry.matcher.SkipPathRequestMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration {

    public static final String SIGNIN_ENTRY_POINT = "/auth/signin";
    public static final String SIGNUP_ENTRY_POINT = "/auth/signup";
    public static final String SWAGGER_ENTRY_POINT = "/swagger-ui/**";
    public static final String API_DOCS_ENTRY_POINT = "/api-docs/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/refreshToken";
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    private final AuthenticationFailureHandler failureHandler;

    public SecurityConfiguration(final AuthEntryPointJwt unauthorizedHandler,
                                 final JwtTokenProvider jwtTokenProvider,
                                 final AuthenticationManager authenticationManager,
                                 @Qualifier("restAuthenticationSuccessHandler") final AuthenticationSuccessHandler authenticationSuccessHandler,
                                 @Qualifier("oauth2AuthenticationSuccessHandler") final AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler,
                                 final AuthenticationFailureHandler failureHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(SIGNIN_ENTRY_POINT).permitAll()
                    .antMatchers(SIGNUP_ENTRY_POINT).permitAll()
                    .antMatchers(SWAGGER_ENTRY_POINT).permitAll()
                    .antMatchers(API_DOCS_ENTRY_POINT).permitAll()
                    .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll()
                    .antMatchers("/login/oauth2/code/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .loginPage("/oauth2Login")
                .loginProcessingUrl("/login/oauth2/code/google")
                .failureHandler(failureHandler)
                .successHandler(oauth2AuthenticationSuccessHandler);

        return http.build();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    protected TokenAuthenticationFilter buildTokenAuthenticationFilter() {
        List<String> pathsToSkip = new ArrayList<>(Arrays.asList(SIGNIN_ENTRY_POINT, SIGNUP_ENTRY_POINT, SWAGGER_ENTRY_POINT, API_DOCS_ENTRY_POINT, TOKEN_REFRESH_ENTRY_POINT, "/login/oauth2/code/*"));
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip);
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(jwtTokenProvider, matcher, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    protected LoginAuthenticationFilter buildLoginProcessingFilter() {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(SIGNIN_ENTRY_POINT, authenticationSuccessHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    protected RefreshTokenAuthenticationFilter buildRefreshTokenProcessingFilter() {
        RefreshTokenAuthenticationFilter filter = new RefreshTokenAuthenticationFilter(jwtTokenProvider, TOKEN_REFRESH_ENTRY_POINT, authenticationSuccessHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
}
