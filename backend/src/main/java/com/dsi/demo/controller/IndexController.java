package com.dsi.demo.controller;

import com.dsi.demo.response.SuccessResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndexController {

    @GetMapping("/")
    public SuccessResponse<String> hello(){
        return new SuccessResponse<>("Index","It works");
    }
}
