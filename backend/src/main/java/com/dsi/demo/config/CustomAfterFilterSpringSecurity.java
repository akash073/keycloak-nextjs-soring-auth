package com.dsi.demo.config;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAfterFilterSpringSecurity  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("CustomAfterFilterSpringSecurity");

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        KeycloakPrincipal principal = (KeycloakPrincipal)authentication.getPrincipal();
        if (principal != null) {
            //user has a valid session, we can assign role on the fly like this
            principal.getKeycloakSecurityContext().getToken().se().getRoles().add("Test-Role");

        }*/

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
