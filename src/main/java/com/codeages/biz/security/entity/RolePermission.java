package com.codeages.biz.security.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codeages.framework.base.BaseEntity;

@Entity
public class RolePermission extends BaseEntity {
	
	@ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;
	
	private String permissionCode;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
}
