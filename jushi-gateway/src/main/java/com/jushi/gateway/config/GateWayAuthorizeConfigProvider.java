package com.jushi.gateway.config;

import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class GateWayAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        //http://localhost:8025/api/web/plate/querySort/SSE
        //http://localhost:8025/api/web/article/page/SSE?page=0&size=9&order=weight
        config
                .pathMatchers(
                        "/api/uaa/**",
                        "/api/web/**",
                        "/api/admin/**")
                .permitAll();
    }
}