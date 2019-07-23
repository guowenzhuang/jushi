package com.jushi.admin.pojo.vo;

import com.jushi.api.pojo.po.CommentPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

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
    private ArticleVo article;
    /**
     * 评论人
     */
    private SysUserVo sysUser;
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
     * 祖先评论 (最父级评论)
     */
    private ArticleCommentVo ancestor;

    public void copyProperties(CommentPO commentPO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(commentPO, this);

        // 拷贝文章
        ArticleVo articleVo = new ArticleVo();
        articleVo.copyProperties(commentPO.getArticle());
        this.article = articleVo;

        // 拷贝用户
        SysUserVo sysUser = new SysUserVo();
        sysUser.copyProperties(commentPO.getSysUser());
        this.sysUser = sysUser;
    }
}
