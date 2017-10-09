package com.edu.biz.security.entity.pojo;

import java.util.Map;

import com.edu.biz.security.entity.User;

public class UserVo extends User {
	private Map<String, Object> Permissions;

	public Map<String, Object> getPermissions() {
		return Permissions;
	}

	public void setPermissions(Map<String, Object> permissions) {
		Permissions = permissions;
	}
}
