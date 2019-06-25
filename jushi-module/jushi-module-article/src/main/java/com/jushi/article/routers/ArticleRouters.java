package com.jushi.article.routers;

import com.jushi.article.handler.ArticleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

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
                        //获取所有文章
                        RequestPredicates.POST("/articleHomePage")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        articleHandler::articleHomePage)

                        .andRoute(RequestPredicates.GET("/stream/articleHomePage"),
                                articleHandler::articleHomePageSSE)
        );
    }
}