package com.edu.biz.security.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "permissionConfig")
@XmlAccessorType (XmlAccessType.FIELD)
public class PermissionConfig {

	@XmlElement(name = "permission",type = Permission.class)
	private List<Permission> permissions;
	
	private List<String> defaultPermission;

	public List<String> getDefaultPermission() {
		return defaultPermission;
	}

	public void setDefaultPermission(List<String> defaultPermission) {
		this.defaultPermission = defaultPermission;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
