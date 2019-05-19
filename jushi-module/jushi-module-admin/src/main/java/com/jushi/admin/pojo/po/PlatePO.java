package com.jushi.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


@Data
@Accessors(chain = true)
@TableName("plate")
public class PlatePO {
    /**
     * id
     */
    @TableId(value = "id",type= IdType.ID_WORKER)
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
