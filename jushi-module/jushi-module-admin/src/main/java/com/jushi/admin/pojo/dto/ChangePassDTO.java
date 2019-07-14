package com.jushi.admin.pojo.dto;

import lombok.Data;

/**
 * @author 173039
 * 修改密码dto
 */
@Data
public class ChangePassDTO {
    /**
     * 用户名称
     */
    private String username;
    /**
     * 原密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}
