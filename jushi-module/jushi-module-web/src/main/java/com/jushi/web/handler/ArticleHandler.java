package com.jushi.web.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.PlatePO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.web.pojo.dto.IssueArticleDTO;
import com.jushi.web.pojo.query.ArticlePageQueryByPlate;
import com.jushi.web.pojo.query.ArticleSearchQuery;
import com.jushi.web.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        return pageQuery(Mono.just(articlePageQuery), query -> {
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
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> articleSearchQueryPageSSE(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        ArticleSearchQuery articleSearchQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticleSearchQuery.class, false);
        return pageQuery(Mono.just(articleSearchQuery), query -> {
            ArticleSearchQuery pageQuery = (ArticleSearchQuery) query;
            return getSearchQuery(pageQuery);
        }, entityFlux -> {
            return sseReturn(entityFlux);
        });
    }


    /**
     * 发文章
     */
    public Mono<ServerResponse> issueArticle(ServerRequest request) {
        Mono<IssueArticleDTO> issueArticleMono = request.bodyToMono(IssueArticleDTO.class);
        return issueArticleMono.flatMap(issueArticle -> {
            CheckException exception = checkIssueArtcle(issueArticle);
            if (exception != null) {
                return Mono.error(exception);
            }

            ArticlePO articlePO = new ArticlePO();
            BeanUtils.copyProperties(issueArticle, articlePO);

            // 用户
            articlePO.setSysUser(SysUserPO.builder().id(issueArticle.getUserId()).build());
            //板块
            articlePO.setPlate(PlatePO.builder().id(issueArticle.getPlateId()).build());
            articlePO.setIsPublic(true);
            articlePO.setScanCount(0L);
            articlePO.setLikeCount(0L);
            articlePO.setCommentCount(0L);
            articlePO.setWeight(0L);
            Mono<ArticlePO> saveArtice = articleRepository.save(articlePO);
            return saveArtice.flatMap(sa -> {
                return ServerResponse.ok()
                        .body(Mono.just(Result.success("发表成功",sa))
                                , Result.class);
            });
        })
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 发表文章 参数不能为null", IssueArticleDTO.class.getName()))), Result.class));

    }

    private CheckException checkIssueArtcle(IssueArticleDTO article) {
        if (StrUtil.isBlank(article.getPlateId())) {
            return new CheckException("板块", "请选择板块");
        }
        if (StrUtil.isBlank(article.getTitle())) {
            return new CheckException("标题", "请输入标题");
        }
        if (article.getTitle().length() < 6 || article.getTitle().length() > 50) {
            return new CheckException("标题", "标题长度在6-50之间");
        }
        return null;
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
        Criteria titleCriteria = Criteria.where("title").regex(StrUtil.format("^.*{}.*$", pageQuery.getSearchContent()), "i");
        //内容模糊查询
        Criteria contentCriteria = Criteria.where("content").regex(StrUtil.format("^.*{}.*$", pageQuery.getSearchContent()), "i");
        Query query = new Query();
        Criteria criteria = new Criteria();
        //or
        criteria.orOperator(titleCriteria, contentCriteria);
        query.addCriteria(criteria);
        return query;
    }


}
