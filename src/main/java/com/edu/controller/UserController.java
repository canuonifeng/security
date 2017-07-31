package com.edu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.entity.validgroup.Create;
import com.edu.biz.security.entity.validgroup.Update;
import com.edu.biz.security.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api("用户")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('user', 'get')")
	@ApiOperation(value = "分页查询用户")
	public Page<User> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 3, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return userService.searchUsers(conditions, pageable);
	}
	
	@RequestMapping(path = "/check_username",method = RequestMethod. GET)
	@ApiOperation(value = "检查用户名是否重复", notes = "根据用户名检查是否重复")
	public Boolean checkUserName(String userName,  Long userId){
		 return userService.checkUserName(userName, userId);
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
	public User edit(@PathVariable Long id, @Validated( { Update.class }) @RequestBody User user) {
		user.setId(id);
		return userService.updateUser(user);
	}
	
	@RequestMapping(path = "/{id}/change_status", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	@ApiOperation(value = "编辑用户信息")
	public User changeUserStatus(@PathVariable Long id, UserStatus status) {
		System.out.println("用户状态："+status);
		return userService.changeUserStatus(id, status);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('user', 'delete')")
	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	public void delete(@PathVariable Long id) {
		userService.deleteUser(id);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('user', 'show')")
	@ApiOperation(value = "查询用户", notes = "根据url的id来查询用户信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
	public User get(@PathVariable Long id){
		return userService.getUserById(id);
	}

	@RequestMapping(path = "/permissions", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/password", method = RequestMethod.PUT)
	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "设置当前用户新密码")
	public boolean setNewPassword(@RequestBody String oldPassword, @RequestBody String newPassword) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.setNewPassword(user.getId(), oldPassword, newPassword);
		return true;
	}
}
