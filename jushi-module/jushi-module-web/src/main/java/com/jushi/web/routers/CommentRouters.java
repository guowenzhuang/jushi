package com.jushi.web.routers;

import com.jushi.api.routers.BaseRouters;
import com.jushi.web.handler.CommentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 评论route
 *
 * @author 80795
 */
@Configuration
public class CommentRouters extends BaseRouters<CommentHandler> {
    @Bean
    public RouterFunction<ServerResponse> commentRouter(CommentHandler commentHandler) {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.POST("/issueComment")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                commentHandler::issueComment)
                .andRoute(
                RequestPredicates.GET("/queryPageArticle"),
                commentHandler::commentQueryPageByArticle)
                .andRoute(
                RequestPredicates.GET("/queryPageArticle/SSE"),
                commentHandler::commentQueryPageByArticleSSE)
                .andRoute(RequestPredicates.GET("/commentDetails/{commentId}"),
                commentHandler::commentChild)
                .andRoute(RequestPredicates.GET("/commentDetails/SSE/{commentId}"),
                        commentHandler::commentChildSSE)
                .andRoute(
                        RequestPredicates.POST("/like")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                        commentHandler::like
                );
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/comment"),
                baseRoute(route, commentHandler)
        );
    }
}
