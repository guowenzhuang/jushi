package com.jushi.auth.server;

import com.jushi.auth.server.repository.support.WiselyRepositoryImpl;
import com.jushi.auth.server.security.SecurityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
public class JuShiAuthServerApplication {
    @Bean(name = "auditorAware")
    public AuditorAware<String> auditorAware() {
        return ()-> java.util.Optional.ofNullable(SecurityUtils.getCurrentUserUsername());
    }
    public static void main(String[] args) {
        SpringApplication.run(JuShiAuthServerApplication.class, args);
    }
}
