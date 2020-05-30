package com.xcodeassociated.commons.utils.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.Set;

public class KeycloakAuth {

    @SuppressWarnings("unchecked")
    public static Optional<KeycloakPrincipal<KeycloakSecurityContext>> findKeycloakPrincipal() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(auth -> auth instanceof KeycloakPrincipal)
                .map(auth -> (KeycloakPrincipal<KeycloakSecurityContext>) auth);
    }

    public static KeycloakPrincipal<KeycloakSecurityContext> getKeycloakPrincipal() {
        return findKeycloakPrincipal()
                .orElseThrow(() -> new IllegalStateException("principal is null"));
    }

    public static Set<String> getRoles() {
        return findRoles()
                .orElseThrow(() -> new IllegalStateException("principal is null"));
    }

    public static Optional<String> getToken() {
        return findKeycloakPrincipal()
                .map(KeycloakPrincipal::getKeycloakSecurityContext)
                .map(KeycloakSecurityContext::getTokenString);
    }

    public static Optional<Set<String>> findRoles() {
        return findKeycloakPrincipal()
                .map(KeycloakPrincipal::getKeycloakSecurityContext)
                .map(KeycloakSecurityContext::getToken)
                .map(AccessToken::getRealmAccess)
                .map(AccessToken.Access::getRoles);
    }

    public static boolean hasRole(String role) {
        return findRoles()
                .map(roles -> roles.contains(role))
                .orElse(false);
    }

}
