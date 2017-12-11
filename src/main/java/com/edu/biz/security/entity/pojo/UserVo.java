package com.edu.biz.security.entity.pojo;

import java.util.Set;

import com.edu.biz.security.entity.User;

public class UserVo extends User {
	private Set<String> permissions;

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		permissions = permissions;
	}
}
