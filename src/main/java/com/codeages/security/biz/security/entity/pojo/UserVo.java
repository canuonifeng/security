package com.codeages.security.biz.security.entity.pojo;

import java.util.Set;

import com.codeages.security.biz.security.entity.User;

public class UserVo extends User {
	private Set<String> permissions;

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}
