package com.dsi.demo.config;

import com.dsi.demo.controller.StudentController;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*
* For details
* https://stackoverflow.com/questions/60147364/using-both-realm-roles-and-ressource-roles-with-keycloak-springsecurity
*/
public class CustomKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomKeycloakAuthenticationProvider.class);

    private GrantedAuthoritiesMapper grantedAuthoritiesMapper;

    public void setGrantedAuthoritiesMapper(GrantedAuthoritiesMapper grantedAuthoritiesMapper) {
        this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        for (String role : token.getAccount().getRoles()) {
            grantedAuthorities.add(new KeycloakRole(role.toUpperCase()));
        }

        logger.error("CustomKeycloakAuthenticationProvider: authenticate");
// ADDING THE MODIFICATION AND ENABLING ROLE FROM RESSOURCE

       // KeycloakSecurityContext keycloakSecurityContext = token.getAccount().getKeycloakSecurityContext();

        Map<String, AccessToken.Access> resourceAccess =  token.getAccount().getKeycloakSecurityContext().getToken().getResourceAccess();

        for(String key : resourceAccess.keySet()) {
            System.out.println(key);

            for (String role : token.getAccount().getKeycloakSecurityContext().getToken().getResourceAccess(key).getRoles()) {
                grantedAuthorities.add(new KeycloakRole(role.toUpperCase()));
            }
        }

        Collection<? extends GrantedAuthority> authorities = mapAuthorities(grantedAuthorities);

        KeycloakAccount keycloakAccount = token.getAccount();



        return new KeycloakAuthenticationToken(keycloakAccount, token.isInteractive(), authorities);
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        return grantedAuthoritiesMapper != null
            ? grantedAuthoritiesMapper.mapAuthorities(authorities)
            : authorities;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return KeycloakAuthenticationToken.class.isAssignableFrom(aClass);
    }


}