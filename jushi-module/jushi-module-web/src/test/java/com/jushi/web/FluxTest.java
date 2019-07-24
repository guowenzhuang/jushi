package com.jushi.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;

public class FluxTest {

    /**
     * 两个flux合并
     */
    @Test
    public void fluxWithList() {
        Flux flux1 = Flux.just(4, 5, 6);
        List<Integer> list = CollectionUtil.list(true, 1, 2, 3);
        Flux flux = flux1.startWith(list);
        flux.subscribe(System.out::println);
    }

}


