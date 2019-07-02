package com.jushi.web.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.web.pojo.query.ArticlePageQueryByPlate;
import com.jushi.web.pojo.query.ArticleSearchQuery;
import com.jushi.web.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author 80795
 * @date 2019/6/23 21:20
 */
@Slf4j
@Component
public class ArticleHandler extends BaseHandler<ArticleRepository, ArticlePO> {
    /**
     * mongo模板
     */
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 根据板块分页查询文章
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleQueryPageByPlate(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        ArticlePageQueryByPlate articlePageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticlePageQueryByPlate.class, false);
        return pageQuery(Mono.just(articlePageQuery), query -> {
            ArticlePageQueryByPlate articlePageQueryByPlate = (ArticlePageQueryByPlate) query;
            return getQueryByPlate(articlePageQueryByPlate);
        }, articlePoFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(articlePoFlux, ArticlePO.class);

        });
    }

    /**
     * 根据板块分页查询文章(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleQueryPageByPlateSSE(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        ArticlePageQueryByPlate articlePageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticlePageQueryByPlate.class, false);
        return pageQuery(Mono.just(articlePageQuery),query -> {
            ArticlePageQueryByPlate articlePageQueryByPlate = (ArticlePageQueryByPlate) query;
            return getQueryByPlate(articlePageQueryByPlate);
        }, entityFlux -> {
            return sseReturn(entityFlux);
        });
    }


    /**
     * 文章搜索
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleSearchQueryPage(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        ArticleSearchQuery articleSearchQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticleSearchQuery.class, false);
        return pageQuery(Mono.just(articleSearchQuery), query -> {
            ArticleSearchQuery pageQuery = (ArticleSearchQuery) query;
            return getSearchQuery(pageQuery);
        }, articlePOFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(articlePOFlux, ArticlePO.class);

        });
    }
    /**
     * 文章搜索(SSE)
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleSearchQueryPageSSE(ServerRequest request){
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        ArticleSearchQuery articleSearchQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticleSearchQuery.class, false);
        return pageQuery(Mono.just(articleSearchQuery),query -> {
            ArticleSearchQuery pageQuery = (ArticleSearchQuery) query;
            return getSearchQuery(pageQuery);
        }, entityFlux -> {
            return sseReturn(entityFlux);
        });
    }
    /**
     * 封装板块查询条件
     *
     * @param pageQuery
     * @return
     */
    private Query getQueryByPlate(ArticlePageQueryByPlate pageQuery) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = criteria.and("plate").is(pageQuery.getPlateId());
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 封装搜索条件查询
     *
     * @param pageQuery
     * @return
     */
    private Query getSearchQuery(ArticleSearchQuery pageQuery) {
        //title模糊查询
        Criteria titleCriteria = Criteria.where("title").regex(StrUtil.format("^.*{}.*$", pageQuery.getSearchContent()),"i");
        //内容模糊查询
        Criteria contentCriteria = Criteria.where("content").regex(StrUtil.format("^.*{}.*$", pageQuery.getSearchContent()),"i");
        Query query =new Query();
        Criteria criteria=new Criteria();
        //or
        criteria.orOperator(titleCriteria, contentCriteria);
        query.addCriteria(criteria);
        return query;
    }
}
