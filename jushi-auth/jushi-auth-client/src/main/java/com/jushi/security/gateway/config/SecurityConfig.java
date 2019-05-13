package com.jushi.security.gateway.config;

import com.jushi.security.client.config.JushiWebAuthorizeConfigManager;
import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired(required = false)
    private List<AuthorizeConfigProvider> authorizeConfigProviders;
    private Logger logger = LoggerFactory.getLogger(JushiWebAuthorizeConfigManager.class);

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {

        http
                .csrf().disable();
        return http.build();
    }


}