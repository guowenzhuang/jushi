package com.jushi.article.handler;


import com.jushi.api.pojo.po.PlatePO;
import com.jushi.article.repository.PlateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author 80795
 * @date 2019/6/27 21:44
 */
@Slf4j
@Component
public class PlateHandler {
    /**
     * 板块Repository
     */
    @Autowired
    private PlateRepository plateRepository;

    /**
     * 板块查询
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> plateQuery(ServerRequest request) {
        return ServerResponse.ok().body(plateRepository.findAll(), PlatePO.class);
    }
}
