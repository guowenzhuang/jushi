package com.jushi.web.handler;

import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.CommentPO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.web.pojo.dto.IssueCommentDTO;
import com.jushi.web.repository.ArticleRepository;
import com.jushi.web.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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

            //父级评论
            if (!StrUtil.isBlank(issueComment.getParentId())) {
                commentPO.setParent(CommentPO.builder().id(issueComment.getParentId()).build());
            }

            //祖先评论
            if (!StrUtil.isBlank(issueComment.getAncestorId())) {
                commentPO.setAncestor(CommentPO.builder().id(issueComment.getAncestorId()).build());
            }


            Mono<CommentPO> saveComment = commentRepository.save(commentPO);
            //评论文章
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
