package com.jushi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jushi.user.pojo.po.SysUserPO;


public interface UserMapper extends BaseMapper<SysUserPO>{

/*    *//**
     * 根据用户名查询用户
     *//*
    @Query("select count(u.id) from SysUser u where u.username=:username")
    Long isUserByName(@Param("username") String username);*/

}
