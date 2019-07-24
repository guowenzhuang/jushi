package com.jushi.web.pojo.query;

import com.jushi.api.pojo.query.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页查询
 * @author 80795
 * @date 2019/6/23 21:43
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class ArticlePageByUserQuery extends PageQuery {
    /**
     * 用户id
     */
    private String userId;
}
