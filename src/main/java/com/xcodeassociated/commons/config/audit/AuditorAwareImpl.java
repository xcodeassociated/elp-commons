package com.xcodeassociated.commons.config.audit;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @SuppressWarnings("unchecked")
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication)) {
            log.trace("current auditor undefined - unauthorized");
            return of("undefined");
        }
        String userName = authentication.getName();

        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)
                    authentication.getPrincipal();

            userName = kp.getKeycloakSecurityContext().getToken().getSubject();
        }
        return ofNullable(userName);
    }
}
