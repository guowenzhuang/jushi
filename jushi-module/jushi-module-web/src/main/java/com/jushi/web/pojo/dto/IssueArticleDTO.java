package com.jushi.web.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发表文章
 * @author mm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueArticleDTO implements Serializable {

    private static final long serialVersionUID = 2660715058723014033L;
    /**
     * 板块id
     */
   private String plateId;
    /**
     * 用户id
     */
   private String userId;
      /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章内容是否有图片
     */
    private Boolean isImage;
    /**
     * 是否公开
     */
   private Boolean isPublic;


}
