package com.dsi.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DeclareRoles;

@RestController
@RequestMapping(path = "students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    @GetMapping("/")
    @Secured("ROLE_STUDENT")
    public String hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "Hello student";
    }
}
