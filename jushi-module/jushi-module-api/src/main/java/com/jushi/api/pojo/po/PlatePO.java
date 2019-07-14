package com.jushi.api.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;


/**
 * @author 80795
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plate")
public class PlatePO implements Serializable {
    private static final long serialVersionUID = 5822372900516005654L;
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
    @Field("click_count")
    private Long clickCount;
    /**
     * 主贴数量
     */
    @Field("topic_count")
    private Long topicCount;
    /**
     * 权重
     */
    private Long weight;
    /**
     * 状态
     */
    private Boolean state;
    /**
     * 帖子列表
     */
    @DBRef
    private List<ArticlePO> articles;

}
