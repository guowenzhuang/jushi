package com.jushi.admin.config;

import com.jushi.security.common.config.AuthorizeConfigProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author 80795
 */
@Component
public class AdminAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ServerHttpSecurity.AuthorizeExchangeSpec config) {
        config
                .pathMatchers("/user/register","/user/changePassword")
                .permitAll();
    }
}