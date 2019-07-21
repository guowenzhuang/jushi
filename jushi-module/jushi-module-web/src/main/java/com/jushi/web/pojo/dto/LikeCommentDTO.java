package com.jushi.web.pojo.dto;

import lombok.Data;

/**
 * 评论点赞
 *
 * @author 80795
 * @date 2019/7/20 16:36
 */
@Data
public class LikeCommentDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 评论id
     */
    private String commentId;
}
