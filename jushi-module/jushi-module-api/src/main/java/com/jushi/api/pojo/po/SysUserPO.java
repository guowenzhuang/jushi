package com.jushi.api.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * 实体类
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "sys_user")
public class SysUserPO implements Serializable {
    private static final long serialVersionUID = -4292493510110770597L;
    @Id
    private String id;
    @Field("created_by")
    private String createdBy;
    @Field("created_date")
    private java.util.Date createdDate;
    @Field("last_modified_by")
    private String lastModifiedBy;
    @Field("last_modified_date")
    private java.util.Date lastModifiedDate;
    private String email;
    @Field("first_name")
    private String firstName;
    @Field("image_url")
    private String imageUrl;
    @Field("last_name")
    private String lastName;
    @JsonIgnore
    private String password;
    private String username;
    @DBRef
    private List<ArticlePO> articles;
    /**
     * 用户所有评论
     */
    @DBRef
    private List<CommentPO> comments;

}
