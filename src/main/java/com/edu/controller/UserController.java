package com.edu.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.User;
import com.edu.biz.security.service.UserService;


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
	@PreAuthorize("hasPermission('user', 'add')")
	public User add(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	public User edit(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('user', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return userService.deleteUser(id);
	}
}
