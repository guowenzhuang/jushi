package com.jushi.user.controller;

import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import com.jushi.user.pojo.po.SysUser;
import com.jushi.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;


	@PostMapping("/register")
	public Result userRegister(@RequestBody  SysUser sysUser){
		if(sysUser==null){
			return new Result(false, StatusCode.PARAMETERILLEGAL.value(),"参数不合法");
		}
		return userService.userRegister(sysUser);
	}

}
