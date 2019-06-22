package com.jushi.admin.routers;

import com.jushi.admin.handler.UserHandler;
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
public class UserRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/user"),
                RouterFunctions.route(
                        //相当于方法上面的GetMapping
                        //获取所有用户
                        RequestPredicates.POST("/register")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        userHandler::userRegister)

        );
    }
}