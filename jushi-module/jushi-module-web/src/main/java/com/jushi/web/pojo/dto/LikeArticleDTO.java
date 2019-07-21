package com.jushi.web.pojo.dto;

import lombok.Data;

/**
 * 文章点赞
 *
 * @author 80795
 * @date 2019/7/20 16:36
 */
@Data
public class LikeArticleDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 文章id
     */
    private String articleId;
}
