package com.jushi.web.config;

import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class WebAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        config
                .pathMatchers(
                        "/**",
                        "/articleHomePage",
                        "/stream/articleHomePage",
                        "/plate")
                .permitAll();
    }
}