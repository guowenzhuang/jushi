package com.jushi.web.handler;


import com.jushi.api.handler.BaseHandlerAbst;
import com.jushi.api.pojo.po.PlatePO;
import com.jushi.web.repository.PlateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author 80795
 * @date 2019/6/27 21:44
 */
@Slf4j
@Component
public class PlateHandler extends BaseHandlerAbst<PlateRepository, PlatePO> {
    /**
     * 板块Repository
     */
    @Autowired
    private PlateRepository plateRepository;

    public Mono<ServerResponse> querySort(ServerRequest request) {
        Flux<PlatePO> platePOFlux = plateRepository.findAll(Sort.by(Sort.Direction.DESC, "weight"));
        return ServerResponse.ok().body(platePOFlux, PlatePO.class);
    }

    public Mono<ServerResponse> querySortSSE(ServerRequest request) {
        Flux<PlatePO> platePOFlux = plateRepository.findAll(Sort.by(Sort.Direction.DESC, "weight"));
        return sseReturn(platePOFlux);
    }

}
