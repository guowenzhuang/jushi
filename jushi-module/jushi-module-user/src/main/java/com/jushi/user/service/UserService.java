package com.jushi.user.service;

import com.jushi.user.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import com.jushi.user.dao.UserDao;

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
	BCryptPasswordEncoder encoder;
	 public SysUser findByRegister(SysUser user){
		SysUser u = new SysUser();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		 List<SysUser> find = userDao.findByUsername(user.getUsername());
		 if (user.getUsername()==null && user.getUsername().equals("")){
			 logger.warn("{}-用户已存在，请选择其他用户名!",user.getUsername());
		 }
		 //注册用户
		 userDao.save(user);
		 logger.info("{}注册成功!",user.getUsername());
		return user;
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
