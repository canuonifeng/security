package com.edu.controller;

import java.util.Map;

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

import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/role")
@Api("角色")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('role', 'add')")
	public Role add(@RequestBody Role role) {
		return roleService.createRole(role);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('role', 'edit')")
	public Role edit(@PathVariable Long id, @RequestBody Role role) {
		return roleService.updateRole(role);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('role', 'delete')")
	public boolean delete(@PathVariable Long id, @RequestBody Role role) {
		return roleService.deleteRole(role.getId());
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('role', 'get')")
	public Role get(@PathVariable Long id) {
		Role role = new Role();
		role.setId(id);
		return roleService.getRole(role.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('role', 'get')")
	public Page<Role> pager(Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return roleService.searchRoles(conditions, pageable);
	}
}
