package com.jushi.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jushi.api.util.IdWorker;
import com.jushi.api.pojo.Result;
import com.jushi.api.util.EmptyUtils;
import com.jushi.user.dao.UserDao;
import com.jushi.user.pojo.po.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IdWorker idWorker;

    public Result userRegister(SysUser user) {
        if (EmptyUtils.isEmpty(user.getUsername())) {
            logger.warn("{}-用户名不能为空", user.getUsername());
            return Result.error("用户名不能为空");
        }
        if (EmptyUtils.isEmpty(user.getPassword())) {
            logger.warn("{}-密码不能为空", user.getPassword());
            return Result.error("密码不能为空");
        }
        //根据名称查找用户
        logger.info("查询是否有重名用户 入参为 username:{}", user.getUsername());
        Long count = userDao.isUserByName(user.getUsername());
        logger.info("查询是否有重名用户成功 返回数据为 count:{}", count);
        //如果传进来的用户名称=null 或者 空字符串
        if (count != 0 && count != null) {
            logger.warn("{}-用户已存在，请选择其他用户名!", user.getUsername());
            return Result.error("用户名称已存在");
        }
        add(user);
        logger.info("{}注册成功!", user.getUsername());
        return Result.success("注册成功");
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<SysUser> findAll() {
        return userDao.findAll();
    }

    private void add(SysUser user) {
        user.setPassword(encoder.encode(user.getPassword()))
                .setCreated_by(user.getUsername())
                .setCreated_date(new Date())
                .setId(idWorker.nextId());
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (IOException e) {
            logger.warn("用户新增转JSON异常");
        }
        logger.info("开始用户新增 入参为 {}", userJson);
        userDao.save(user);
        logger.info("用户新增成功");
    }

}
