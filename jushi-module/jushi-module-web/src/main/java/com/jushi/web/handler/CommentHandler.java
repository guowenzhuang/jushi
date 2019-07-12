package com.jushi.web.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.CommentPO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.web.pojo.consts.CommentConst;
import com.jushi.web.pojo.dto.IssueCommentDTO;
import com.jushi.web.pojo.query.CommentPageQueryByArticle;
import com.jushi.web.pojo.vo.ArticleCommentVo;
import com.jushi.web.repository.ArticleRepository;
import com.jushi.web.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 80795
 * @date 2019/7/7 20:50
 */
@Slf4j
@Component
public class CommentHandler extends BaseHandler<CommentRepository, CommentPO> {
    /**
     * mongo模板
     */
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;


    /**
     * 根据文章查找评论
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> commentQueryPageByArticle(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        CommentPageQueryByArticle commentPageQueryByArticle = BeanUtil.mapToBean(params.toSingleValueMap(), CommentPageQueryByArticle.class, false);
        return popularCommentpageQuery(Mono.just(commentPageQueryByArticle), commentPOFlux -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(commentPOFlux, ArticleCommentVo.class);
        });
    }


    /**
     * 根据文章查找评论(SSE)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> commentQueryPageByArticleSSE(ServerRequest request) {
        //转换参数
        MultiValueMap<String, String> params = request.queryParams();
        CommentPageQueryByArticle commentPageQueryByArticle = BeanUtil.mapToBean(params.toSingleValueMap(), CommentPageQueryByArticle.class, false);
        return popularCommentpageQuery(Mono.just(commentPageQueryByArticle), entityFlux -> {
            return sseReturn(entityFlux, ArticleCommentVo.class);
        });
    }


    private Mono<ServerResponse> popularCommentpageQuery(Mono<CommentPageQueryByArticle> pageQueryMono,
                                                           Function<Flux<ArticleCommentVo>, Mono<ServerResponse>> returnFunc) {
        return pageQueryMono.flatMap(pageQuery -> {
            checkPage(pageQuery);

            //查找点赞最高的三个评论
            PageRequest likeCountPageRequest = PageRequest.of(0, CommentConst.PAGECOMMENTLIKETOPCOUNT, Sort.Direction.DESC, "like_count");
            Query likeQuery = new Query().with(likeCountPageRequest);
            Flux<CommentPO> likeTopComment = reactiveMongoTemplate.find(likeQuery, CommentPO.class);
            Mono<List<CommentPO>> listMono = likeTopComment.collectList();
            return listMono.flatMap(likeComment -> {
                List<CommentPO> likeList = likeComment.stream()
                        .filter(comment -> comment.getLikeCount() != null && comment.getLikeCount() > 0)
                        .collect(Collectors.toList());


                Criteria articleLikeCriteria = Criteria.where("article").is(pageQuery.getArticleId());
                //不包含点赞的评论
                Object[] likeIds = likeList.stream().map(CommentPO::getId).toArray();
                articleLikeCriteria.and("id").nin(likeIds);

                //父级评论为null的
                articleLikeCriteria.and("parent").is(null);

                Query query = new Query();
                query.addCriteria(articleLikeCriteria);
                String[] order = {"createTime"};
                pageQuery.setOrder(order);
                Pageable pageable = getPageable(pageQuery);
                Query with = query.with(pageable);
                Mono<Long> count = reactiveMongoTemplate.count(with, CommentPO.class);
                return count.flatMap(sums -> {
                    long size = pageQuery.getPage() * pageQuery.getSize();
                    if (sums.longValue() == size) {
                        return sSEReponseBuild(Mono.just(Result.error("无数据")), Result.class);
                    }
                    //分页数据
                    Flux<CommentPO> commentPOFlux = reactiveMongoTemplate.find(with, CommentPO.class);
                    commentPOFlux=commentPOFlux.startWith(likeList);
                    Flux<ArticleCommentVo> articleCommentVoFlux = commentPOFlux.map(item -> {
                        ArticleCommentVo articleCommentVo = new ArticleCommentVo();
                        BeanUtils.copyProperties(item, articleCommentVo);
                        return articleCommentVo;
                    });
                    return returnFunc.apply(articleCommentVoFlux);
                });
            });


        }).switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("分页查询{}参数不能为null", CommentPO.class.getName()))), Result.class));
    }

    /**
     * 发表评论
     */
    public Mono<ServerResponse> issueComment(ServerRequest request) {
        Mono<IssueCommentDTO> IssueCommentMono = request.bodyToMono(IssueCommentDTO.class);
        return IssueCommentMono.flatMap(issueComment -> {
            //校验
            CheckException exception = checkIssueArtcle(issueComment);
            if (exception != null) {
                return Mono.error(exception);
            }

            //拷贝属性
            CommentPO commentPO = new CommentPO();
            //拷贝基本属性
            BeanUtils.copyProperties(issueComment, commentPO);
            //文章
            commentPO.setArticle(ArticlePO.builder().id(issueComment.getArticleId()).build());
            //用户
            commentPO.setSysUser(SysUserPO.builder().id(issueComment.getSysUserId()).build());

            //评论时间
            commentPO.setCreateTime(new Date());

            //父级评论
            if (!StrUtil.isBlank(issueComment.getParentId())) {
                commentPO.setParent(CommentPO.builder().id(issueComment.getParentId()).build());
                // 获取父级评论
                Mono<CommentPO> parentCommentMono = commentRepository.findById(issueComment.getParentId());
                parentCommentMono.subscribe(parentComment -> {
                    //父级评论 保存子级评论
                    parentComment.addChilder(commentPO);
                    Long commentCount = parentComment.getCommentCount();
                    parentComment.setCommentCount(commentCount == null ? 1 : commentCount + 1);
                    commentRepository.save(parentComment).subscribe();
                });
            }

            //祖先评论
            if (!StrUtil.isBlank(issueComment.getAncestorId())) {
                commentPO.setAncestor(CommentPO.builder().id(issueComment.getAncestorId()).build());
                // 获取祖先评论
                Mono<CommentPO> ancestorCommentMono = commentRepository.findById(issueComment.getAncestorId());
                ancestorCommentMono.subscribe(ancestorComment -> {

                });
            }

            //评论文章
            Mono<CommentPO> saveComment = commentRepository.save(commentPO);

            Mono<ArticlePO> articlePOMono = articleRepository.findById(issueComment.getArticleId());
            return saveComment.flatMap(comment -> {

                return articlePOMono.flatMap(articlePO -> {
                    //评论+1
                    Long commentCount = articlePO.getCommentCount();
                    articlePO.setCommentCount(commentCount == null ? 1 : commentCount + 1);
                    return articleRepository.save(articlePO).flatMap(svArticle -> {
                        return ServerResponse.ok()
                                .body(Mono.just(Result.success("发表成功", comment))
                                        , Result.class);
                    });

                });
            });
        })
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 发表评论 参数不能为null", IssueCommentDTO.class.getName()))), Result.class));
    }


    /**
     * 文章发表校验
     *
     * @param comment
     * @return
     */
    private CheckException checkIssueArtcle(IssueCommentDTO comment) {
        if (StrUtil.isBlank(comment.getSysUserId())) {
            return new CheckException("用户", "请登录");
        }
        if (StrUtil.isBlank(comment.getArticleId())) {
            return new CheckException("文章", "请选择文章");
        }
        if (StrUtil.isBlank(comment.getContent())) {
            return new CheckException("内容", "请输入内容");
        }
        return null;
    }


}
