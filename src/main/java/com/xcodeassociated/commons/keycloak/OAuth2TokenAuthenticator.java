package com.xcodeassociated.commons.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2TokenAuthenticator implements Authenticator {

    private final TokenService tokenService;
    private final TokenHolder tokenHolder;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        TokenData tokenData = tokenService.login()
                .blockingGet();
        tokenHolder.set(tokenData);
        return response.request().newBuilder()
                .header(AUTHORIZATION, tokenData.bearerToken())
                .build();
    }

}
