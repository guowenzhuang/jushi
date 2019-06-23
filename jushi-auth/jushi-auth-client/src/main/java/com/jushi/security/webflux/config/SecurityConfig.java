package com.jushi.security.webflux.config;

import com.jushi.security.common.config.AuthorizeConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired(required = false)
    private AuthorizeConfigManager authorizeConfigManager;
    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
            authorizeConfigManager.config(http.authorizeExchange());
        return
                http
                .csrf().disable()
                .exceptionHandling()
                //.authenticationEntryPoint(new HttpBasicServerAuthenticationEntryPoint())
                .and()
                .httpBasic()
                .and()
                .build();
    }


}