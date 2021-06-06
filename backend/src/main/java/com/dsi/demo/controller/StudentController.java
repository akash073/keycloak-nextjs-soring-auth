package com.dsi.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DeclareRoles;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    @GetMapping("/")
    public String hello(){
        return "Hello student";
    }
}
