package com.jushi.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class CommentPO {
    /**
     * 评论id
     */
    @Id
    private String id;
    /**
     * 文章id
     */
    @Field("article_id")
    private String articleId;
    /**
     * 板块id
     */
    private String pid;
    /**
     * 评论内容
     */
    private String content;
    /**
     *评论用户id
     */
    @Field("from_uid")
    private String fromUid;
    /**
     *评论目标用户id
     */
    @Field("to_uid")
    private String toUid;
    /**
     * 父级评论id
     */
    private String parentid;
    /**
     * 子级评论id
     */
    private String childrenid;
}