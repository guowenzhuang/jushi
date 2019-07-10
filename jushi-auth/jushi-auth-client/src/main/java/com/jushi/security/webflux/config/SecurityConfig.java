package com.jushi.security.webflux.config;

import com.jushi.security.common.config.AuthorizeConfigManager;
import com.jushi.security.jwt.JWTHeadersExchangeMatcher;
import com.jushi.security.jwt.TokenAuthenticationConverter;
import com.jushi.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired(required = false)
    private AuthorizeConfigManager authorizeConfigManager;
    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {

        ServerHttpSecurity serverHttpSecurity = http
                .addFilterAt(webFilter(), SecurityWebFiltersOrder.AUTHORIZATION);

        authorizeConfigManager.config(serverHttpSecurity.authorizeExchange());

        return serverHttpSecurity.csrf().disable()
                .build();


    }


    @Bean
    public AuthenticationWebFilter webFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(repositoryReactiveAuthenticationManager());
        authenticationWebFilter.setAuthenticationConverter(new TokenAuthenticationConverter(tokenProvider));
        authenticationWebFilter.setRequiresAuthenticationMatcher(new JWTHeadersExchangeMatcher());
        authenticationWebFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        return authenticationWebFilter;
    }

    @Bean
    public JWTReactiveAuthenticationManager repositoryReactiveAuthenticationManager() {
        JWTReactiveAuthenticationManager repositoryReactiveAuthenticationManager = new JWTReactiveAuthenticationManager();
        return repositoryReactiveAuthenticationManager;
    }


}