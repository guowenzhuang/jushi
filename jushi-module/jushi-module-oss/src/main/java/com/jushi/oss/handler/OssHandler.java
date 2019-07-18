package com.jushi.oss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author 80795
 * @date 2019/7/17 21:06
 */
@Component
public class OssHandler {

    /**
     * 文件上传 gridfs
     */
    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 文件上传
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> upload(ServerRequest request) {
        Mono<MultiValueMap<String, Part>> multiValueMapMono = request.body(BodyExtractors.toMultipartData());
        return multiValueMapMono.flatMap(multiValueMap -> {
            System.out.println(multiValueMap);
            return null;
        });
    }
}
