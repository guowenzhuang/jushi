package com.jushi.user.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="sys_user")
@Data
@Accessors(chain = true)
public class SysUser implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;//
    private String created_by;//
    private java.util.Date created_date;//
    private String last_modified_by;//
    private java.util.Date last_modified_date;//
    private String email;//
    private String first_name;//
    private String image_url;//
    private String last_name;//
    private String password;//
    private String username;//


}
