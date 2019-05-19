package com.jushi.user.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserPO implements Serializable{
    @TableId(value = "id",type= IdType.ID_WORKER)
    private Long id;
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
