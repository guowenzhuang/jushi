package com.jushi.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章
 *
 * @author 80795
 * @date 2019/06/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "article")
public class ArticlePO implements Serializable {
    private static final long serialVersionUID = 4829545493606099519L;
    @Id
    private String id;
    /**
     * 板块id
     */
    @DBRef
    private PlatePO plate;

    /**
     * 所属用户
     */
    @DBRef
    private SysUserPO sysUser;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 帖子封面
     */
    private String cover;
    /**
     * 文章内容是否有图片
     */
    @Field("is_image")
    private Boolean isImage;
    /**
     * 文章发表时间
     */
    @Field("create_time")
    private Date createTime;
    /**
     * 文章最后修改时间
     */
    @Field("update_time")
    private Date updateTime;
    /**
     * 是否公开
     */
    @Field("is_public")
    private Boolean isPublic;
    /**
     * 是否置顶
     */
    @Field("is_top")
    private Boolean isTop;
    /**
     * 是否热门
     */
    @Field("is_popular")
    private Boolean isPopular;
    /**
     * 浏览量
     */
    @Field("scan_count")
    private Long scanCount;
    /**
     * 点赞数
     */
    @Field("like_count")
    private Long likeCount;
    /**
     * 评论数
     */
    @Field("comment_count")
    private Long commentCount;
    /**
     * 审核是否通过
     */
    private Boolean auditState;
    /**
     * 权重(默认按权重排序)
     */
    private Long weight;
    /**
     * 文章所有评论
     */
    private List<CommentPO> comments;
}
