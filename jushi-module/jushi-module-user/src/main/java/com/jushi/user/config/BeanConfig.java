package com.jushi.user.config;

import com.jushi.api.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class BeanConfig {
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
