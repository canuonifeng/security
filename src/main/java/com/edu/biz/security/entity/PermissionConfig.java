package com.edu.biz.security.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("permissionConfig")
public class PermissionConfig {

	@XStreamImplicit(itemFieldName = "permission")
	private List<Permission2> permissions;

	public List<Permission2> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission2> permissions) {
		this.permissions = permissions;
	}
}
