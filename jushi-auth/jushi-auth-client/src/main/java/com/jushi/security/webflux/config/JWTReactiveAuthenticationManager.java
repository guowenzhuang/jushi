package com.jushi.security.webflux.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author duc-d
 */
public class JWTReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return Mono.just(authentication);
        }
        return Mono.just(authentication)
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(this::authenticateToken)
                .publishOn(Schedulers.parallel())
                .onErrorResume(e -> raiseBadCredentials())
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .map(u -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), u.getAuthorities()));
    }

    private <T> Mono<T> raiseBadCredentials() {
        return Mono.error(new BadCredentialsException("Invalid Credentials"));
    }

    private Mono<UserDetails> authenticateToken(final UsernamePasswordAuthenticationToken authenticationToken) {
        String username = authenticationToken.getName();

        logger.info("checking authentication for user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("authenticated user " + username + ", setting security context");
            return null;
        }

        return null;
    }
}
