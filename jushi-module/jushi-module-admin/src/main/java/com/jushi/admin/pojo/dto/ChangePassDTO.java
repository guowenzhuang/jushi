package com.jushi.admin.pojo.dto;

import lombok.Data;

/**
 * 修改密码dto
 */
@Data
public class ChangePassDTO {
    private String id;
    private String username;
    private String password;
}
