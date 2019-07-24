package com.jushi.web.pojo.vo;

import com.jushi.api.pojo.po.ArticlePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo implements Serializable {
    private static final long serialVersionUID = 4829545493606099519L;
    private String id;

    /**
     * 所属用户
     */
    private SysUserVo sysUser;
    /**
     * 板块
     */
    private PlateVo plate;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 帖子封面
     */
    private String cover;
    /**
     * 文章内容是否有图片
     */
    private Boolean isImage;
    /**
     * 文章发表时间
     */
    private Date createTime;
    /**
     * 文章最后修改时间
     */
    private Date updateTime;
    /**
     * 是否公开
     */
    private Boolean isPublic;
    /**
     * 是否置顶
     */
    private Boolean isTop;
    /**
     * 是否热门
     */
    private Boolean isPopular;
    /**
     * 浏览量
     */
    private Long scanCount;
    /**
     * 点赞数
     */
    private Long likeCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 审核是否通过
     */
    private Boolean auditState;
    /**
     * 权重(默认按权重排序)
     */
    private Long weight;

    public void copyProperties(ArticlePO articlePO) {
        // 拷贝基本属性
        BeanUtils.copyProperties(articlePO, this);

        // 拷贝用户
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.copyProperties(articlePO.getSysUser());
        this.sysUser = sysUserVo;

        // 拷贝板块
        PlateVo plateVo = new PlateVo();
        plateVo.copyProperties(articlePO.getPlate());
        this.plate = plateVo;
    }

}
