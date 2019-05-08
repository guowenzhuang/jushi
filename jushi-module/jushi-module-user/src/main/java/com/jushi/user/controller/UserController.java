package com.jushi.user.controller;

import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import com.jushi.user.pojo.SysUser;
import com.jushi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@RequestMapping(method= RequestMethod.GET)
	public Result findByRegister(SysUser sysUser){
		if(sysUser==null){
			return new Result(false, StatusCode.PARAMETERILLEGAL.value(),"参数不合法");
		}
		return userService.userRegister(sysUser);
	}

}
