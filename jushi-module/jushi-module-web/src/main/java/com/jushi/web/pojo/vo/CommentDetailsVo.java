package com.jushi.web.pojo.vo;

import com.jushi.api.pojo.po.ArticlePO;
import com.jushi.api.pojo.po.CommentPO;
import com.jushi.api.pojo.po.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论详情vo
 *
 * @author 80795
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailsVo implements Serializable {
    private static final long serialVersionUID = -423459611534212221L;
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
    @JsonIgnore
    private CommentDetailsVo parent;
    /**
     * 子级评论
     */
    private List<CommentDetailsVo> children;
    /**
     * 祖先评论 (最父级评论)
     */
    @JsonIgnore
    private CommentDetailsVo ancestor;

    public void setChildren(List<CommentPO> childrens) {
        if (childrens == null) {
            return;
        }
        this.children = childrens.stream().map(item -> {
            CommentDetailsVo commentDetailsVo = new CommentDetailsVo();
            BeanUtils.copyProperties(item, commentDetailsVo);
            commentDetailsVo.setChildren(item.getChildren());
            return commentDetailsVo;
        }).collect(Collectors.toList());

    }

}