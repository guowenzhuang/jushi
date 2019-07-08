package com.jushi.api.routers;

import com.jushi.api.handler.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author 80795
 * @date 2019/6/28 23:51
 */
public abstract class BaseRouters<T extends BaseHandler> {

    protected RouterFunction<ServerResponse> baseRoute(T baseHandler) {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.POST("/")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                baseHandler::add
        );
        return baseRoute(route, baseHandler, false);
    }

    protected RouterFunction<ServerResponse> baseRoute(RouterFunction routerFunction, T baseHandler) {
        return baseRoute(routerFunction, baseHandler, true);
    }

    /**
     * 加载路由
     * @param routerFunction routerFunction
     * @param baseHandler   handler
     * @param isAdd       是否包含新增
     * @return
     */
    protected RouterFunction<ServerResponse> baseRoute(RouterFunction routerFunction, T baseHandler, boolean isAdd) {
        if (isAdd) {
            routerFunction = routerFunction.andRoute(
                    RequestPredicates.POST("/")
                            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
                    baseHandler::add
            );
        }
        return routerFunction.andRoute(
                RequestPredicates.GET("/"),
                baseHandler::queryAll
        ).andRoute(
                RequestPredicates.GET("/SSE"),
                baseHandler::queryAllSSE
        ).andRoute(
                RequestPredicates.GET("/page"),
                baseHandler::page
        ).andRoute(
                RequestPredicates.GET("/page/SSE"),
                baseHandler::pageSSE
        ).andRoute(
                RequestPredicates.DELETE("/{id}"),
                baseHandler::delete
        ).andRoute(
                RequestPredicates.PUT("/{id}"),
                baseHandler::update
        ).andRoute(
                RequestPredicates.GET("/{id}"),
                baseHandler::queryById
        );
    }
}
