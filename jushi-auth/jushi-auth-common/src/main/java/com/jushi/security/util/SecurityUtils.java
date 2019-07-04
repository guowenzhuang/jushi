package com.jushi.security.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getTokenFromRequest(ServerWebExchange serverWebExchange) {
        String token = serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        return StringUtils.isEmpty(token) ? Strings.EMPTY : token;
    }

    public static Mono<String> getUserFromRequest(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getPrincipal()
                .cast(UsernamePasswordAuthenticationToken.class)
                .map(UsernamePasswordAuthenticationToken::getPrincipal)
                .cast(String.class);
    }
}
