package com.edu.biz.user.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Permission  extends BaseEntity{

	private String name;

	@ManyToMany(mappedBy = "role")
	private Collection<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}