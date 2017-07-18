package com.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Role add(@RequestBody Role role) {
		return roleService.createRole(role);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT)
	public Role edit(@RequestBody Role role) {
		return roleService.createRole(role);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public Role delete(@RequestBody Role role) {
		return roleService.createRole(role);
	}
}
