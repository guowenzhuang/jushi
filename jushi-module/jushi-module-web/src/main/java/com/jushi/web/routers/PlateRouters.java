package com.jushi.web.routers;

import com.jushi.api.routers.BaseRouters;
import com.jushi.web.handler.PlateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 80795
 * @date 2019/6/27 22:00
 */
@Configuration
public class PlateRouters extends BaseRouters<PlateHandler> {
    @Bean
    public RouterFunction<ServerResponse> plateRouter(PlateHandler plateHandler) {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.GET("/querySort"),
                plateHandler::querySort)
                .andRoute(
                        RequestPredicates.GET("/querySort/SSE"),
                        plateHandler::querySortSSE);
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/plate"),
                baseRoute(route, plateHandler)
        );
    }
}
