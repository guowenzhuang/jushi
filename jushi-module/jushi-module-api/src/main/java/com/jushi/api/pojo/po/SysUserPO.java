package com.jushi.api.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体类
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "sys_user")
public class SysUserPO implements Serializable {
    private static final long serialVersionUID = -4292493510110770597L;
    @Id
    private String id;
    @Field("created_by")
    private String createdBy;
    @Field("created_date")
    private Date createdDate;
    @Field("last_modified_by")
    private String lastModifiedBy;
    @Field("last_modified_date")
    private Date lastModifiedDate;
    private String email;
    @Field("first_name")
    private String firstName;
    @Field("image_url")
    private String imageUrl;
    @Field("last_name")
    private String lastName;
    @JsonIgnore
    private String password;
    private String username;
    @DBRef
    private List<ArticlePO> articles;
    /**
     * 用户所有评论
     */
    @DBRef
    private List<CommentPO> comments;
    /**
     * 所以点过赞的评论
     */
    @DBRef
    @Field("like_comments")
    private List<CommentPO> likeComments;
    /**
     * 所以点过赞的文章
     */
    @DBRef
    @Field("like_articles")
    private List<ArticlePO> likeArticles;

    /**
     * 新增点赞文章
     *
     * @return
     */
    public SysUserPO likeArticle(ArticlePO articlePO, boolean isLike) {
        if (this.likeArticles == null) {
            this.likeArticles = new ArrayList<>();
        }
        if (isLike) {
            this.likeArticles=this.likeArticles.stream().filter(item -> !item.getId().equals(articlePO.getId())).collect(Collectors.toList());
        } else
            this.likeArticles.add(articlePO);
        return this;
    }

    /**
     * 新增点赞评论
     *
     * @return
     */
    public SysUserPO addLikeCoomment(CommentPO commentPO, boolean isLike) {
        if (this.likeComments == null) {
            this.likeComments = new ArrayList<>();
        }
        if (isLike) {
            this.likeComments=this.likeComments.stream().filter(item -> !item.getId().equals(commentPO.getId())).collect(Collectors.toList());
        } else
            this.likeComments.add(commentPO);
        return this;
    }
}
