package com.jushi.admin.pojo.vo;

import com.jushi.api.pojo.po.PlatePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author 80795
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlateVo {
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 点击次数
     */
    private Long clickCount;
    /**
     * 主贴数量
     */
    private Long topicCount;
    /**
     * 权重
     */
    private Long weight;
    /**
     * 状态
     */
    private Boolean state;

    public void copyProperties(PlatePO platePO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(platePO, this);
    }
}