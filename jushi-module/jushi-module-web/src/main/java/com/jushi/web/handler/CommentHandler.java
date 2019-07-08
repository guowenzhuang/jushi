package com.jushi.web.handler;

import cn.hutool.core.util.StrUtil;
import com.jushi.api.exception.CheckException;
import com.jushi.api.handler.BaseHandler;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.CommentPO;
import com.jushi.api.pojo.po.SysUserPO;
import com.jushi.web.pojo.dto.IssueArticleDTO;
import com.jushi.web.pojo.dto.IssueCommentDTO;
import com.jushi.web.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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


    /**
     * 发表评论
     */
      public Mono<ServerResponse> issueComment(ServerRequest request){
        Mono<IssueCommentDTO> IssueCommentMono = request.bodyToMono(IssueCommentDTO.class);
        return IssueCommentMono.flatMap(issueComment -> {
        CheckException exception = checkIssueArtcle(issueComment);
            if (exception != null) {
                return Mono.error(exception);
            }
            CommentPO commentPO = new CommentPO();
            BeanUtils.copyProperties(issueComment, commentPO);
            //用户
            commentPO.setSysUser(SysUserPO.builder().id(issueComment.getSysUserId()).build());
            //获取父id
            commentPO.setParent(CommentPO.builder().id(issueComment.getParentId()).build() );

            Mono<CommentPO> saveComment = commentRepository.save(commentPO);
            return saveComment.flatMap(sa -> {
                return ServerResponse.ok()
                        .body(Mono.just(Result.success("发表成功",sa))
                                , Result.class);
            });
        })
                .switchIfEmpty(ServerResponse.ok().body(Mono.just(Result.error(StrUtil.format("{} 发表评论 参数不能为null", IssueCommentDTO.class.getName()))), Result.class));
    }

    private CheckException checkIssueArtcle(IssueCommentDTO comment) {
        if (StrUtil.isBlank(comment.getArticleId())) {
            return new CheckException("文章", "请选择文章");
        }
        if (StrUtil.isBlank(comment.getContent())) {
            return new CheckException("内容", "请输入内容");
        }
        return null;
    }



}
