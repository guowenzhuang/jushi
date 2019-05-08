package com.jushi.user.service;

import com.jushi.api.pojo.Result;
import com.jushi.user.dao.UserDao;
import com.jushi.user.pojo.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserDao userDao;
	@Autowired
    PasswordEncoder encoder;
	 public Result userRegister(SysUser user){
		//根据名称查找用户
         Long count = userDao.isUserByName(user.getUsername());
		 //如果传进来的用户名称=null 或者 空字符串
		 if (count!=0 || count!=null){
			 logger.warn("{}-用户已存在，请选择其他用户名!",user.getUsername());
			 return Result.error("用户名称已存在");
		 }
		 add(user);
		 logger.info("{}注册成功!",user.getUsername());
		return Result.success("注册成功");
    }
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<SysUser> findAll() {
		return userDao.findAll();
	}

      public void add(SysUser user){
		  String newpassword = encoder.encode(user.getPassword());
		   userDao.save(user);

	};

}
