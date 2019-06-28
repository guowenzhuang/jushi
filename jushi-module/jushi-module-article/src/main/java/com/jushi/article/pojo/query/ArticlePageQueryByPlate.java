package com.jushi.article.pojo.query;

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
public class ArticlePageQueryByPlate {
    /**
     * 板块id
     */
    private String plateId;
    /**
     * 页数
     */
    private Integer page;
    /**
     *
     */
    private Integer size;
}
