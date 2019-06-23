package com.jushi.article.routers;

import com.jushi.article.handler.ArticleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 80795
 * @date 2019/6/23 21:20
 */
@Configuration
public class ArticleRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(ArticleHandler articleHandler) {
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/"),
                RouterFunctions.route(
                        //相当于方法上面的GetMapping
                        //获取所有用户
                        RequestPredicates.POST("/articleHomePage")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        articleHandler::articleHomePage)

        );
    }
}
