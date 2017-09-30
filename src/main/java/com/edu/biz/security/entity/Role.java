package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String code;

	@ManyToMany(mappedBy = "roles")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<User> users;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "role")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<RolePermission> rolePermissions;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		for(RolePermission rolePermission : rolePermissions) {
			rolePermission.setRole(this);
		}
		this.rolePermissions = rolePermissions;
	}
}
