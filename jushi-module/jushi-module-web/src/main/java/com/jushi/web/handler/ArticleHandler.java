package com.jushi.web.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.PlatePO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.api.pojo.query.PageQuery;
import com.jushi.api.util.CheckUtil;
import com.jushi.web.pojo.dto.IssueArticleDTO;
import com.jushi.web.pojo.dto.LikeArticleDTO;
import com.jushi.web.pojo.query.ArticlePageByUserQuery;
import com.jushi.web.pojo.query.ArticlePageQueryByPlate;
import com.jushi.web.pojo.query.ArticleSearchQuery;
import com.jushi.web.pojo.vo.ArticleVo;
import com.jushi.web.repository.ArticleRepository;
import com.jushi.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

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
    @Autowired
    private UserRepository userRepository;


    /**
     * 根据id查询文章
     *
     * @param request
     * @return
     */
    @Override
    public Mono<ServerResponse> queryById(ServerRequest request) {
        //获取路径的id
        String id = request.pathVariable("id");
        if (StrUtil.isBlank(id)) {
            CheckUtil.checkEmpty("id", id);
        }
        Mono<ArticlePO> mono = articleRepository.findById(id);
        return mono.flatMap(po -> {
            ArticleVo articleVo = new ArticleVo();
            articleVo.copyProperties(po);
            return ServerResponse.ok().body(Mono.just(articleVo), ArticleVo.class);
        })
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("id:{} 找不到此数据", id))), Result.class));
    }

    /**
     * 根据用户分页查询(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> pageByUserSSE(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();
        ArticlePageByUserQuery pageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), ArticlePageByUserQuery.class, false);
        return articlePageByUserQuer(Mono.just(pageQuery), query -> {
            return getQueryByUser(query);
        }, entityFlux -> {
            return sseReturn(entityFlux, ArticleVo.class);
        });
    }

    /**
     * 分页查询条件封装
     *
     * @param pageQuery
     * @return
     */
    protected Mono<Query> getQueryByUser(ArticlePageByUserQuery pageQuery) {
        Mono<SysUserPO> sysUserPOMono = userRepository.findById(pageQuery.getUserId());
        return sysUserPOMono.map(userPO -> {
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("sysUser").is(userPO);
            query.addCriteria(criteria);
            return query;
        });
    }

    private Mono<ServerResponse> articlePageByUserQuer(Mono<ArticlePageByUserQuery> pageQueryMono,
                                                       Function<ArticlePageByUserQuery, Mono<Query>> queryFunction,
                                                       Function<Flux<ArticleVo>, Mono<ServerResponse>> returnFunc) {
        return pageQueryMono.flatMap(pageQuery -> {
            checkPage(pageQuery);
            Mono<Query> queryMono = queryFunction.apply(pageQuery);
            return queryMono.flatMap(query -> {
                Pageable pageable = getPageable(pageQuery);
                Query with = query.with(pageable);
                Mono<Long> count = reactiveMongoTemplate.count(with, ArticlePO.class);
                return count.flatMap(sums -> {
                    long size = pageQuery.getPage() * pageQuery.getSize();
                    if (sums.longValue() == size) {
                        return sSEReponseBuild(Mono.just(Result.error("无数据")), Result.class);
                    }
                    //获取数据
                    Flux<ArticlePO> entityFlux = reactiveMongoTemplate.find(with, ArticlePO.class);
                    Flux<ArticleVo> articleVoFlux = entityFlux.map(item -> {
                        ArticleVo articleVo = new ArticleVo();
                        articleVo.copyProperties(item);
                        return articleVo;
                    });
                    return returnFunc.apply(articleVoFlux);
                });
            });
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("分页查询{}参数不能为null", ArticlePO.class.getName()))), Result.class));

    }

    /**
     * 分页查询(SSE)
     *
     * @param request
     * @return
     */
    @Override
    public Mono<ServerResponse> pageSSE(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();
        PageQuery pageQuery = BeanUtil.mapToBean(params.toSingleValueMap(), PageQuery.class, false);
        return articlePageQuery(Mono.just(pageQuery), query -> {
            return getQuery(query);
        }, entityFlux -> {
            return sseReturn(entityFlux, ArticleVo.class);
        });
    }


    private Mono<ServerResponse> articlePageQuery(Mono<PageQuery> pageQueryMono,
                                                  Function<PageQuery, Query> queryFunction,
                                                  Function<Flux<ArticleVo>, Mono<ServerResponse>> returnFunc) {
        return pageQueryMono.flatMap(pageQuery -> {
            checkPage(pageQuery);
            Query query = queryFunction.apply(pageQuery);
            Pageable pageable = getPageable(pageQuery);
            Query with = query.with(pageable);
            Mono<Long> count = reactiveMongoTemplate.count(with, ArticlePO.class);
            return count.flatMap(sums -> {
                long size = pageQuery.getPage() * pageQuery.getSize();
                if (sums.longValue() == size) {
                    return sSEReponseBuild(Mono.just(Result.error("无数据")), Result.class);
                }
                //获取数据
                Flux<ArticlePO> entityFlux = reactiveMongoTemplate.find(with, ArticlePO.class);
                Flux<ArticleVo> articleVoFlux = entityFlux.map(item -> {
                    ArticleVo articleVo = new ArticleVo();
                    articleVo.copyProperties(item);
                    return articleVo;
                });
                return returnFunc.apply(articleVoFlux);
            });
        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("分页查询{}参数不能为null", ArticlePO.class.getName()))), Result.class));

    }

    /**
     * 文章点赞
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> like(ServerRequest request) {
        Mono<LikeArticleDTO> likeArticleDTOMono = request.bodyToMono(LikeArticleDTO.class);
        return likeArticleDTOMono.flatMap(likeArticleDTO -> {
            // 字段校验
            if (StrUtil.isBlank(likeArticleDTO.getArticleId())) {
                return Mono.error(new CheckException("articleId", likeArticleDTO.getArticleId()));
            }
            if (StrUtil.isBlank(likeArticleDTO.getUserId())) {
                return Mono.error(new CheckException("userId", likeArticleDTO.getUserId()));
            }
            return userRepository.findById(likeArticleDTO.getUserId()).flatMap(user -> {
                if (user.getLikeArticles() == null) {
                    user.setLikeArticles(new ArrayList<>());
                }
                boolean isLike = user.getLikeArticles().stream().map(ArticlePO::getId)
                        .anyMatch(articleId -> articleId.equals(likeArticleDTO.getArticleId()));
                // 点赞
                return like(likeArticleDTO, user, isLike);
            });
        });
    }

    private Mono<ServerResponse> like(LikeArticleDTO likeArticleDTO, SysUserPO user, Boolean isLike) {
        Mono<ArticlePO> articlePOMono = articleRepository.findById(likeArticleDTO.getArticleId());
        // 文章点赞数+1
        return articlePOMono.flatMap(articlePO -> {

            Long likeCount = articlePO.getLikeCount();
            if (likeCount == null) likeCount = 0L;
            if (isLike)
                articlePO.setLikeCount(likeCount - 1);
            else
                articlePO.setLikeCount(likeCount + 1);
            return articleRepository.save(articlePO).flatMap(articlePOUpdate -> {
                // 用户新增点赞文章
                user.likeArticle(articlePOUpdate, isLike);
                return userRepository.save(user).flatMap(u -> {
                    if (isLike)
                        return ServerResponse.ok().body(Mono.just(Result.error("已取消")), Result.class);
                    else
                        return ServerResponse.ok().body(Mono.just(Result.success("点赞成功")), Result.class);
                });
            });
        });
    }

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
        return articlePageQuery(Mono.just(articlePageQuery), query -> {
            ArticlePageQueryByPlate articlePageQueryByPlate = (ArticlePageQueryByPlate) query;
            return getQueryByPlate(articlePageQueryByPlate);
        }, articlePoFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(articlePoFlux, ArticleVo.class);

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
        return articlePageQuery(Mono.just(articlePageQuery), query -> {
            ArticlePageQueryByPlate articlePageQueryByPlate = (ArticlePageQueryByPlate) query;
            return getQueryByPlate(articlePageQueryByPlate);
        }, entityFlux -> {
            return sseReturn(entityFlux, ArticleVo.class);
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
            BeanUtil.copyProperties(issueArticle, articlePO);

            // 用户
            articlePO.setSysUser(SysUserPO.builder().id(issueArticle.getUserId()).build());
            //板块
            articlePO.setPlate(PlatePO.builder().id(issueArticle.getPlateId()).build());
            // 判断是否存在图片
            if (articlePO.getContent().indexOf("<img src=\"data:image") >= 0) {
                articlePO.setIsImage(Boolean.TRUE);
            }
            //默认值
            articlePO.setIsPublic(true);
            articlePO.setScanCount(0L);
            articlePO.setLikeCount(0L);
            articlePO.setCommentCount(0L);
            articlePO.setWeight(0L);
            articlePO.setCreateTime(new Date());

            // 内容转换 取得50个字

            Mono<ArticlePO> saveArtice = articleRepository.save(articlePO);
            return saveArtice.flatMap(sa -> {
                return ServerResponse.ok()
                        .body(Mono.just(Result.success("发表成功", sa))
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

    private Mono<UserDetails> getCurrentUser() {
        Mono<UserDetails> user = ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(item -> (UserDetails) item);
        return user;
    }

    private Mono<SysUserPO> getCuuentUserPo() {
        Mono<UserDetails> currentUserMono = getCurrentUser();
        return currentUserMono.flatMap(currentUser -> {
            String username = currentUser.getUsername();
            Example<SysUserPO> exampleUser = Example.of(SysUserPO.builder().username(username).build());
            Mono<SysUserPO> user = userRepository.findOne(exampleUser);
            return user;
        });
    }


}
