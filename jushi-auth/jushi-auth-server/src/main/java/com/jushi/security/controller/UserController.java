package com.jushi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * created by duc-d on 8/5/2018
 */
@RestController
public class UserController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Mono<String> hello(@RequestParam String name) {
        return Mono.just("Hello " + name);
    }

    @GetMapping("/user")
    public Mono<Object> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal);}

}
