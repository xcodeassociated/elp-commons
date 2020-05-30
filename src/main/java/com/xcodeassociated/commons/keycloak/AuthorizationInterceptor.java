package com.xcodeassociated.commons.keycloak;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@Component
public class AuthorizationInterceptor implements Interceptor {

    private final TokenHolder tokenHolder;

    private AuthorizationInterceptor(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        tokenHolder.get().ifPresent(tokenData -> builder.header(AUTHORIZATION, tokenData.bearerToken()));
        return chain.proceed(builder.build());
    }
}
