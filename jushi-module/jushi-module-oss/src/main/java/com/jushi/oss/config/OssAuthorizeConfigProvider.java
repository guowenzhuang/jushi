package com.jushi.oss.config;

import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class OssAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        config
                .pathMatchers("/oss.**")
                .permitAll();
    }
}