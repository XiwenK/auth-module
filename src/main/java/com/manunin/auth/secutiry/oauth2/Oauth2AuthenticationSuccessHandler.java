package com.manunin.auth.secutiry.oauth2;

import com.manunin.auth.exception.ErrorCode;
import com.manunin.auth.exception.ServiceException;
import com.manunin.auth.model.User;
import com.manunin.auth.secutiry.jwt.JwtPair;
import com.manunin.auth.secutiry.jwt.JwtTokenProvider;
import com.manunin.auth.service.UserDetailsImpl;
import com.manunin.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component(value = "oauth2AuthenticationSuccessHandler")
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final UserService userService;

    public Oauth2AuthenticationSuccessHandler(final JwtTokenProvider tokenProvider,
                                              final OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
                                              final UserService userService) {
        this.tokenProvider = tokenProvider;
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = token.getPrincipal().getAttribute("email");
        boolean existsByEmail = userService.existsByEmail(email);
        User user;
        try {
            if (!existsByEmail) {
                user = userService.addUser(new User(email, email, email, token.getPrincipal().getAttribute("given_name"), token.getPrincipal().getAttribute("family_name")));
            } else {
                user = userService.findByEmail(email);
            }
        } catch (ServiceException e) {
            throw new AuthenticationException(ErrorCode.GENERAL.name(), new ServiceException(ErrorCode.GENERAL, "User with email " + email + " already exists"));
        }

        JwtPair jwtPair = tokenProvider.generateTokenPair(UserDetailsImpl.build(user));

        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl("http://localhost:9000/login", jwtPair));
    }

    String getRedirectUrl(final String baseUrl, final JwtPair tokenPair) {
        String url = baseUrl;
        if (baseUrl.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "/?";
        }
        return url + "accessToken=" + tokenPair.getToken() + "&refreshToken=" + tokenPair.getRefreshToken();
    }
}