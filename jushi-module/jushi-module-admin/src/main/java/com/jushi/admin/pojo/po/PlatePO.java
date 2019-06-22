package com.jushi.admin.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plate")
public class PlatePO {
    /**
     * id
     */
    @Id
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
    private String state;
}
