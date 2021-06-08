package com.dsi.demo;

import org.springframework.stereotype.Component;

@Component
public class SampleService {

    public String getUserSecret(){
        return "abc";
    };
}
