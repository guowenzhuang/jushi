package com.jushi.api.pojo.query;

import lombok.Data;

/**
 * @author 80795
 * @date 2019/6/28 23:38
 */
@Data
public class PageQuery {
    /**
     * 页数
     */
    private Integer page;
    /**
     * 条数
     */
    private Integer size;
    /**
     * 排序字段
     */
    private String[] order;
}
