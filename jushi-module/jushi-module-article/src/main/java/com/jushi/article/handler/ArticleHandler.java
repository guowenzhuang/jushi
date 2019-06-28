package com.jushi.article.handler;

import cn.hutool.core.bean.BeanUtil;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.util.CheckUtil;
import com.jushi.article.pojo.query.ArticlePageQuery;
import com.jushi.article.pojo.query.ArticlePageQueryByPlate;
import com.jushi.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

/**
 * @author 80795
 * @date 2019/6/23 21:20
 */
@Slf4j
@Component
public class ArticleHandler {
    /**
     * mongo模板
     */
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    private ArticleRepository articleRepository;

    public Mono<ServerResponse> articeQueryByPate(ServerRequest request){
        //转换参数
        MultiValueMap<String, String> multiValueMap = request.queryParams();
        ArticlePageQueryByPlate articlePageQuery = BeanUtil.mapToBean(multiValueMap.toSingleValueMap(),ArticlePageQueryByPlate.class,false);
        return null;
    }
    /**
     * 帖子首页分页查询
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleHomePage(ServerRequest request) {
        Mono<ArticlePageQuery> articlePageQueryMono = request.bodyToMono(ArticlePageQuery.class);
        return articleHomePageWith(articlePageQueryMono, articlePOFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(articlePOFlux, ArticlePO.class);
        });
    }

    /**
     * 帖子首页分页查询(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleHomePageSSE(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> multiValueMap = request.queryParams();
        ArticlePageQuery articlePageQuery = BeanUtil.mapToBean(multiValueMap.toSingleValueMap(),ArticlePageQuery.class,false);
        return articleHomePageWith(Mono.just(articlePageQuery), articlePOFlux -> {
            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).header("X-Accel-Buffering", "no").body(articlePOFlux, ArticlePO.class);
        });

    }


    private Mono<ServerResponse> articleHomePageWith(Mono<ArticlePageQuery> articlePageQueryMono, Function<Flux<ArticlePO>, Mono<ServerResponse>> function) {
        return articlePageQueryMono.flatMap(articlePageQuery -> {
            checkArticleHomePage(articlePageQuery);
            //封装查询条件
            Query query = getQuery(articlePageQuery);
            //排序条件
            Sort sort = new Sort(Sort.Direction.DESC, "weight");
            //分页
            Pageable pageable = PageRequest.of(articlePageQuery.getPage(), articlePageQuery.getSize(), sort);
            //获取数据
            Flux<ArticlePO> articlePOFlux = reactiveMongoTemplate.find(query.with(pageable), ArticlePO.class);
            return function.apply(articlePOFlux);
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error("分页查询帖子参数不能为null")), Result.class));

    }

    private Query getQuery(ArticlePageQuery articlePageQuery) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria);
        return query;
    }

    private void checkArticleHomePage(ArticlePageQuery articlePageQuery) {
        CheckUtil.checkEmpty("页数", articlePageQuery.getPage());
        CheckUtil.checkEmpty("条数", articlePageQuery.getSize());
    }
}
