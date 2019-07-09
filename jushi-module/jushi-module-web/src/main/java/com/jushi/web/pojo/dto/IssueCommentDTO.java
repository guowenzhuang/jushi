package com.jushi.web.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发表评论
 * @author 80795
 * @date 2019/7/7 21:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueCommentDTO implements Serializable {
    private static final long serialVersionUID = 119943990348348086L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 用户id
     */
    private String sysUserId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 父级评论id
     */
    private String parentId;
    /**
     * 祖先评论id
     */
    private String ancestorId;
}
