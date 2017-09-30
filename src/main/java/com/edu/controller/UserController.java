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

import com.edu.biz.org.entity.OrgJsonViews;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.service.UserService;
import com.edu.biz.validgroup.Create;
import com.edu.biz.validgroup.Update;
import com.edu.biz.viewgroup.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/user")
@Api("用户")
public class UserController extends BaseController<User> {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('user', 'get')")
	@JsonView({ JsonViews.Cascade.class })
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	@ApiOperation(value = "分页查询用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名，完全匹配", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码, 从0开始", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "每页数据数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序,允许多次出现，格式：[字段名,排序方式]。例：id,desc", dataType = "String", paramType = "query"), })
	public Page<User> pager(@RequestParam @ApiIgnore Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = {
					"id" }, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
		return userService.searchUsers(conditions, pageable);
	}

	@RequestMapping(path = "{userId}/removeorg", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	public User removeOrgUser(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		user.setOrg(null);
		return userService.updateUser(user);
	}
	
	@RequestMapping(path = "/checkusername", method = RequestMethod.GET)
	@ApiOperation(value = "检查用户名是否重复", notes = "根据用户名检查是否重复")
	public Boolean checkUserName(String userName, Long userId) {
		return userService.checkUserName(userName, userId);
	}
	
	@RequestMapping(path = "/checkemail", method = RequestMethod.GET)
	@ApiOperation(value = "检查邮箱是否重复", notes = "根据邮箱检查是否重复")
	public Boolean checkEmail(String email, Long userId) {
		return userService.checkEmail(email, userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('user', 'add')")
	@ApiOperation(value = "新增用户", notes = "根据提交的数据创建新用户")
	@JsonView({ JsonViews.NoCascade.class })
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public User add(@Validated({ Create.class }) @RequestBody User user) {
		return userService.createUser(user);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	@ApiOperation(value = "编辑用户信息")
	@JsonView({ JsonViews.NoCascade.class })
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public User edit(@PathVariable @ApiParam(name = "id", value = "用户ID", required = true) Long id,
			@Validated({ Update.class }) @RequestBody User user) {
		user.setId(id);
		return userService.updateUser(user);
	}

	@RequestMapping(path = "/{id}/status", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('user', 'edit')")
	@ApiOperation(value = "修改用户状态")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public User changeUserStatus(@PathVariable @ApiParam(name = "id", value = "用户ID", required = true) Long id,
			@RequestBody @ApiParam(name = "status", value = "enable(启用)，disable(禁用)", required = true) Map<String, String> params) {
		UserStatus status = UserStatus.valueOf(params.get("status"));
		return userService.changeUserStatus(id, status);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('user', 'delete')")
	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public void delete(@PathVariable @ApiParam(name = "id", value = "用户ID", required = true) Long id) {
		userService.deleteUser(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('user', 'get')")
	@ApiOperation(value = "查询用户", notes = "根据url的id来查询用户信息")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	@JsonView({ OrgJsonViews.CascadeParent.class })
	public User get(@PathVariable @ApiParam(name = "id", value = "用户ID", required = true) Long id) {
		User user = userService.getUserById(id);
		return user;
	}

	@RequestMapping(path = "/permissions", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	@ApiOperation(value = "获取当前用户权限", notes = "如果当前用户是超级管理员，则返回{isAdmin:true};若不是超级管理员，以数组的方式返回权限code列表")
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
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public boolean setNewPassword(@RequestBody @ApiParam Map<String, String> params) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String oldPassword = params.get("oldPassword");
		String newPassword = params.get("newPassword");
		userService.setNewPassword(user.getId(), oldPassword, newPassword);
		return true;
	}
	
	@RequestMapping(path = "/current", method = RequestMethod.GET)
	@ApiOperation(value = "获取当前登录用户信息")
	@JsonView({ OrgJsonViews.CascadeParent.class })
	public User getCurrentUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getUserById(user.getId());
	}
}
