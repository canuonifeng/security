package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(path = "/role", method = RequestMethod.POST)
	public Role add(@RequestBody Role role) {
		return roleService.createRole(role);
	}
	
	@RequestMapping(path = "/role/{id}", method = RequestMethod.PUT)
	public Role edit(@PathVariable Long id, @RequestBody Role role) {
		return roleService.updateRole(role);
	}
	
	@RequestMapping(path = "/role/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable Long id, @RequestBody Role role) {
		return roleService.deleteRole(role.getId());
	}
	
	@RequestMapping(path = "/role/{id}", method = RequestMethod.GET)
	public Role get(@PathVariable Long id) {
		Role role = new Role();
		role.setId(id);
		return roleService.getRole(role.getId());
	}
	
	@RequestMapping(path = "/role", method = RequestMethod.GET)
	public Page<Role> pager(Map<String, String> conditions, @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return roleService.searchRoles(conditions, pageable);
	}
}
