package com.jushi.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jushi.api.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker();
    }
}
