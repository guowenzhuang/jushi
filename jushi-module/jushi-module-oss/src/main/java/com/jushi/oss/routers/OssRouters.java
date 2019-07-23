package com.jushi.oss.routers;

import com.jushi.oss.handler.OssHandler;
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
public class OssRouters {
    @Bean
    public RouterFunction<ServerResponse> articleRouter(OssHandler ossHandler) {
        return RouterFunctions.nest(
                //相当于类上面的@RequestMapping
                RequestPredicates.path("/file"),
                RouterFunctions.route(
                        RequestPredicates.POST("/upload/aliyun")
                                .and(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA)),
                        ossHandler::upload).andRoute(
                        RequestPredicates.POST("/upload/gridfs")
                                .and(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA)),
                        ossHandler::gridfsUpload
                )
        );
    }
}