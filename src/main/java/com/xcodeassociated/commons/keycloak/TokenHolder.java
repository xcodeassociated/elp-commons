package com.xcodeassociated.commons.keycloak;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TokenHolder {

    private TokenData tokenData;

    synchronized Optional<TokenData> get() {
        return Optional.ofNullable(tokenData);
    }

    synchronized void set(TokenData data) {
        this.tokenData = data;
    }

}
