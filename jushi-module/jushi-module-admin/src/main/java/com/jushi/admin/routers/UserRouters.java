package com.jushi.admin.routers;

import com.jushi.admin.handler.UserHandler;
import com.jushi.api.routers.BaseRouters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 80795
 */
@Configuration
public class UserRouters extends BaseRouters<UserHandler> {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                //相当于方法上面的GetMapping
                //获取所有用户
                RequestPredicates.POST("/register")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                userHandler::userRegister)
                .andRoute(
                        RequestPredicates.GET("/getCurrentUser"),
                        userHandler::getCurrentUser
                ).andRoute(
                        RequestPredicates.PUT("/changePassword")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        userHandler::changePassword
                ).andRoute(
                        RequestPredicates.PUT("/userSetAvatar")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        userHandler::userSetAvatar
                );
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/user"),
                baseRoute(route, userHandler)
        );
    }
}