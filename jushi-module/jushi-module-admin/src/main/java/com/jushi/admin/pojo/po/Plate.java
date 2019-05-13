package com.jushi.admin.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plate")
@Data
@Accessors(chain = true)
public class Plate {
    /**
     * id
     */
    @Id
    private Long id;
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
