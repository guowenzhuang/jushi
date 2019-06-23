package com.jushi.api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页返回
 * @author 80795
 * @date 2019/6/23 21:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    private Long count;
    private T rows;
}
