package com.xcodeassociated.commons.keycloak;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class TokenService {

    private final TokenApi tokenApi;
    private final String username;
    private final String clientId;
    private final String grantType;
    private final String password;

    public TokenService(TokenApi tokenApi,
                        @Value("${external.token.username}") String username,
                        @Value("${external.token.clientId}") String clientId,
                        @Value("${external.token.grantType}") String grantType,
                        @Value("${external.token.password}") String password) {
        this.tokenApi = tokenApi;
        this.username = username;
        this.grantType = grantType;
        this.clientId = clientId;
        this.password = password;
    }

    Single<TokenData> login() {
        return tokenApi.login(username, password, clientId, grantType);
    }

}
