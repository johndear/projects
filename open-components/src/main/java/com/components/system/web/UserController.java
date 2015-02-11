package com.it.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.it.model.UserEntity;
import com.it.service.IUserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
//	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "list")
	@ResponseBody
	public String list(UserEntity user,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "5") int rows) {
		
		List<UserEntity> list = userService.query();
		System.out.println(list);
		
		return null;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	
	

}
