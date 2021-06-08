package com.dsi.demo.controller;

import org.keycloak.KeycloakPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);


    @GetMapping("/")
    public String hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<SimpleGrantedAuthority> grantedAuthorities = authentication.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList()); // (1)

        KeycloakPrincipal principal = (KeycloakPrincipal)authentication.getPrincipal();
        System.out.println(principal);

        Map<String, Object> customProperty = principal.getKeycloakSecurityContext().getToken().getOtherClaims();

        logger.info(currentPrincipalName);
        return "Hello student";
    }
}
