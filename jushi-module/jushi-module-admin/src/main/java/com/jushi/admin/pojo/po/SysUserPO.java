package com.jushi.admin.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(collection = "sys_user")
public class SysUserPO{
    @Id
    private String id;
    private String created_by;
    private java.util.Date created_date;
    private String last_modified_by;
    private java.util.Date last_modified_date;
    private String email;
    private String first_name;
    private String image_url;
    private String last_name;
    private String password;
    private String username;


}
