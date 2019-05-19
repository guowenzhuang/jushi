package com.jushi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jushi.api.pojo.Result;
import com.jushi.api.util.EmptyUtils;
import com.jushi.user.mapper.UserMapper;
import com.jushi.user.pojo.po.SysUserPO;
import com.jushi.user.service.UserService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;


/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUserPO> implements UserService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;

    public Result userRegister(SysUserPO user) {
        if (EmptyUtils.isEmpty(user.getUsername())) {
            log.warn("{}-用户名不能为空", user.getUsername());
            return Result.error("用户名不能为空");
        }
        if (EmptyUtils.isEmpty(user.getPassword())) {
            log.warn("{}-密码不能为空", user.getPassword());
            return Result.error("密码不能为空");
        }
        //根据名称查找用户
        log.info("查询是否有重名用户 入参为 username:{}", user.getUsername());
        LambdaQueryWrapper<SysUserPO> wrapper=new QueryWrapper().lambda();
        wrapper.eq(SysUserPO::getUsername,user.getUsername());
        Integer count =  baseMapper.selectCount(wrapper);
        log.info("查询是否有重名用户成功 返回数据为 count:{}", count);
        //如果传进来的用户名称=null 或者 空字符串
        if (count != 0 && count != null) {
            log.warn("{}-用户已存在，请选择其他用户名!", user.getUsername());
            return Result.error("用户名称已存在");
        }
        add(user);
        log.info("{}注册成功!", user.getUsername());
        return Result.success("注册成功");
    }


    private void add(SysUserPO user) {
        user.setPassword(encoder.encode(user.getPassword()))
                .setCreated_by(user.getUsername())
                .setCreated_date(new Date());
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (IOException e) {
            log.warn("用户新增转JSON异常");
        }
        log.info("开始用户新增 入参为 {}", userJson);
        baseMapper.insert(user);
        log.info("用户新增成功");
    }

}
