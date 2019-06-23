package com.jushi.security.common.config;

import org.springframework.security.config.web.server.ServerHttpSecurity;

/**
 * @author 80795
 * @date 2018/11/12 8:55
 */
public interface AuthorizeConfigManager {
    void config(ServerHttpSecurity.AuthorizeExchangeSpec config);
}