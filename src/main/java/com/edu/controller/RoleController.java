package com.edu.controller;

import java.util.List;
import java.util.Map;

import org.hibernate.loader.plan.exec.process.spi.ReaderCollector;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.PermissionConfig;
import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;
import com.edu.biz.security.util.ReaderPermissionConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/role")
@Api("角色")
public class RoleController extends BaseController<Role> {

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
	public boolean delete(@PathVariable @ApiParam(name = "id", value = "角色ID", required = true) Long id) {
		return roleService.deleteRole(id);
	}
	
	@RequestMapping(path = "/checkcode",method = RequestMethod. GET)
	@ApiOperation(value = "检查角色代码是否重复", notes = "根据角色代码检查是否重复")
	public Boolean checkCode(String code,  Long roleId){
		 return roleService.checkCode(code, roleId);
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
	public Page<Role> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return roleService.searchRoles(conditions, pageable);
	}
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<Role> findRoles(@RequestParam Map<String, Object> conditions) {
		List<Role> list = roleService.findRoles(conditions);
		
		return list;
	}
	
	@RequestMapping(path = "/permission/tree", method = RequestMethod.GET)
	public PermissionConfig getPermissionTree(@RequestParam Map<String, Object> conditions) {
		PermissionConfig config = ReaderPermissionConfig.readerConfig();
		
		return config;
	}
}
