package com.jushi.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="sys_user")
public class SysUser implements Serializable{

    @Id
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


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated_by() {
        return created_by;
    }
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public java.util.Date getCreated_date() {
        return created_date;
    }
    public void setCreated_date(java.util.Date created_date) {
        this.created_date = created_date;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }
    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public java.util.Date getLast_modified_date() {
        return last_modified_date;
    }
    public void setLast_modified_date(java.util.Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }



}
