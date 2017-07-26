package com.edu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.validgroup.Create;
import com.edu.biz.security.entity.validgroup.Update;
import com.edu.biz.security.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('user', 'get')")
	@ApiOperation(value = "分页查询用户")
	public Page<User> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return userService.searchUsers(conditions, pageable);
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('user', 'add')")
	@ApiOperation(value = "新增用户", notes = "根据提交的数据创建新用户")
	public User add(@Validated( { Create.class }) @RequestBody User user) {
		return userService.createUser(user);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	@ApiOperation(value = "编辑用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "username", value = "登录名", required = true, dataType = "String")
	})
	public User edit(@PathVariable Long id, @Validated( { Update.class }) @RequestBody User user) {
		user.setId(id);
		return userService.updateUser(user);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('user', 'delete')")
	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
	public void delete(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@RequestMapping(path = "/permission", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "获取当前用户权限", notes = "以数组的方式返回权限code列表")
	public Map<String, Object> findCurrentUserPermissionCodes() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.isAdmin()) {
			map.put("isAdmin", true);
		} else {
			map.put("isAdmin", false);
			map.put("permissionCodes", userService.findCurrentUserPermissionCodes());
		}
		return map;
	}
}
