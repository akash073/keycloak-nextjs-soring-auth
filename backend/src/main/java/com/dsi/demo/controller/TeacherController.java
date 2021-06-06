package com.dsi.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {

    @GetMapping("/")
    public String hello(){
        return "Hello teacher";
    }
}
