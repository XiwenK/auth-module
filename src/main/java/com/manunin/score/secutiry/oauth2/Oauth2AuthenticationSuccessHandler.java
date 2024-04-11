package com.manunin.score.secutiry.oauth2;

import com.manunin.score.secutiry.jwt.JwtPair;
import com.manunin.score.model.User;
import com.manunin.score.secutiry.jwt.JwtTokenProvider;
import com.manunin.score.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(value = "oauth2AuthenticationSuccessHandler")
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public Oauth2AuthenticationSuccessHandler(final JwtTokenProvider tokenProvider,
                                              final OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.tokenProvider = tokenProvider;
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getPrincipal().getName());

        JwtPair jwtPair = tokenProvider.generateTokenPair(UserDetailsImpl.build(new User("manunin", "manunin", "manunin")));

        //set jwtPair to cookie and send redirect to frontend
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl("http://localhost:9000/login", jwtPair));

//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        JsonUtils.writeValue(response.getWriter(), jwtPair);
//        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl("http://localhost:9000/login", jwtPair));
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