package com.jushi.web.pojo.query;

import com.jushi.api.pojo.query.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 板块查询
 * @author 80795
 * @date 2019/6/23 21:43
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageQueryByArticle extends PageQuery {
    /**
     * 文章id
     */
    private String articleId;
}
