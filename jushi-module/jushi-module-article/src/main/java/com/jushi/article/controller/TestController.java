package com.jushi.article.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "ssss";
    }

    @GetMapping("/demo")
    @PreAuthorize("hasAuthority('query-demo')")
    public String getDemo(){
        return "good";
    }

    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
