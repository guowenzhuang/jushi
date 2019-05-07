package com.jushi.user.controller;
import com.jushi.user.pojo.Result;
import com.jushi.user.pojo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jushi.user.service.UserService;

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
	public Result findByRegister(@RequestParam("username") String username, @RequestParam("password")String password){
		userService.findByRegister(username,password);
		return new Result(true, StatusCode.OK,"登录成功");
	}

}
