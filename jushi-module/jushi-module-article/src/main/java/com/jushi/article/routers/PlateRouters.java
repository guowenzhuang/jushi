package com.jushi.article.routers;

import com.jushi.article.handler.PlateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 80795
 * @date 2019/6/27 22:00
 */
@Configuration
public class PlateRouters {
    @Bean
    RouterFunction<ServerResponse> plateHandle(PlateHandler plateHandler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/plate"),
                RouterFunctions.route(
                        //获取所有文章
                        RequestPredicates.GET("/")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        plateHandler::plateQuery)
        );
    }
}
