package com.xcodeassociated.commons.config.audit;

import com.xcodeassociated.commons.envers.RevEntity;
import org.hibernate.envers.RevisionListener;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.isNull;

public class RevListener implements RevisionListener {

    @SuppressWarnings("unchecked")
    @Override
    public void newRevision(Object revisionEntity) {
        RevEntity exampleRevEntity = (RevEntity) revisionEntity;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication)) {
            exampleRevEntity.setUsername("undefined");
            return;
        }

        String userName = authentication.getName();
        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)
                    authentication.getPrincipal();

            userName = kp.getKeycloakSecurityContext().getToken().getSubject();
        }
        exampleRevEntity.setUsername(userName);
    }
}
