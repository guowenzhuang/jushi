package com.jushi.web.pojo.vo;

import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子详情页评论VO
 *
 * @author 80795
 * @date 2019/7/11 12:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleCommentVo implements Serializable {

    private static final long serialVersionUID = 7329143560027946520L;
    /**
     * 评论id
     */
    private String id;
    /**
     * 评论文章
     */
    private ArticlePO article;
    /**
     * 评论人
     */
    private SysUserPO sysUser;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private Date createTime;
    /**
     * 点赞数
     */
    private Long likeCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 父级评论
     */
    private ArticleCommentVo parent;
    /**
     * 子级评论
     */
    private List<ArticleCommentVo> popularChildren;
    /**
     * 回复人
     */
    private SysUserPO replyUser;
    /**
     * 祖先评论 (最父级评论)
     */
    private ArticleCommentVo ancestor;
}
