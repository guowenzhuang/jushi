package com.jushi.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 评论
 *
 * @author 80795
 * @date 2019/06/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class CommentPO implements Serializable {
    private static final long serialVersionUID = -4247971141632607641L;
    /**
     * 评论id
     */
    @Id
    private String id;
    /**
     * 评论文章
     */
    @DBRef
    private ArticlePO article;
    /**
     * 评论人
     */
    @DBRef
    private SysUserPO sysUser;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 父级评论
     */
    @DBRef
    private CommentPO parent;
    /**
     * 子级评论
     */
    @DBRef
    private List<CommentPO> children;
    /**
     * 祖先评论 (最父级评论)
     */
    @DBRef
    private CommentPO ancestor;
}