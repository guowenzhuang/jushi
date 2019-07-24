package com.jushi.admin.pojo.dto;

import lombok.Data;

@Data
public class UserAvatarUpdate {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 头像路径
     */
    private String imgUrl;
}
