package com.edu.biz.security.entity.pojo;

import java.util.Set;

import com.edu.biz.security.entity.User;

public class UserVo extends User {
	private Set<String> Permissions;

	public Set<String> getPermissions() {
		return Permissions;
	}

	public void setPermissions(Set<String> permissions) {
		Permissions = permissions;
	}
}
