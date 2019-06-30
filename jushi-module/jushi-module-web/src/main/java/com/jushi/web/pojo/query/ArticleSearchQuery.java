package com.jushi.web.pojo.query;

import com.jushi.api.pojo.query.PageQuery;
import lombok.Data;

/**
 * @author 80795
 * @date 2019/6/30 11:24
 */
@Data
public class ArticleSearchQuery extends PageQuery {
    /**
     * 搜索内容
     */
    private String searchContent;
}
