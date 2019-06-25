package com.jushi.article.config;

import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class ArticleAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        config
                .pathMatchers("/articleHomePage","/stream/articleHomePage")
                .permitAll();
    }
}