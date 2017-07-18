package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.user.entity.User;
import com.edu.biz.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public Page<User> pager(Map<String, String> conditions,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return userService.searchUsers(conditions, pageable);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User add(@RequestBody User user) {
		return userService.createUser(user);
	}

}
