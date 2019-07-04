package com.jushi.security.webflux.config;

import com.jushi.security.common.config.AuthorizeConfigManager;
import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JushiWebAuthorizeConfigManager implements AuthorizeConfigManager {
    @Autowired(required = false)
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        if(authorizeConfigProviders==null){
            return;
        }
        for (AuthorizeConfigProvider authorizeConfigProvider:authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        config.anyExchange().authenticated();
    }

}