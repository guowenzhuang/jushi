package com.jushi.admin.pojo.vo;

import com.jushi.api.pojo.po.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = -4292493510110770597L;
    @Id
    private String id;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String email;
    private String firstName;
    private String imageUrl;
    private String lastName;
    private String username;
    /**
     * 所以点过赞的评论
     */
    @DBRef
    @Field("like_comments")
    private List<ArticleCommentVo> likeComments;
    /**
     * 所以点过赞的文章
     */
    @DBRef
    @Field("like_articles")
    private List<ArticleVo> likeArticles;


    public void copyPropertiesMain(SysUserPO sysUserPO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(sysUserPO, this);

        // 拷贝喜欢的文章
        if (sysUserPO.getLikeArticles() != null) {
            this.likeArticles = sysUserPO.getLikeArticles().stream().map(articlePO -> {
                ArticleVo articleVo = new ArticleVo();
                articleVo.copyProperties(articlePO);
                return articleVo;
            }).collect(Collectors.toList());
        }


        // 拷贝喜欢的评论
        if (sysUserPO.getLikeComments() != null) {
            this.likeComments = sysUserPO.getLikeComments().stream().map(commentPO -> {
                ArticleCommentVo articleCommentVo = new ArticleCommentVo();
                articleCommentVo.copyProperties(commentPO);
                return articleCommentVo;
            }).collect(Collectors.toList());
        }
    }

    public void copyProperties(SysUserPO sysUserPO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(sysUserPO, this);
        this.setLikeArticles(null);
        this.setLikeComments(null);

    }
}
