package com.jushi.security.jwt;

import com.jushi.security.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author duc-d
 */
public class TokenAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {
	private static final String BEARER = "Bearer ";
	private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();
	private static final Function<String, String> isolateBearerValue = authValue -> authValue.substring(BEARER.length(), authValue.length());

	private final TokenProvider tokenProvider;

	public TokenAuthenticationConverter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
		return Mono.justOrEmpty(serverWebExchange)
						.map(SecurityUtils::getTokenFromRequest)
						.filter(Objects::nonNull)
						.filter(matchBearerLength)
						.map(isolateBearerValue)
						.filter(token -> !StringUtils.isEmpty(token))
						.map(tokenProvider::getAuthentication)
						.filter(Objects::nonNull);
	}
}
