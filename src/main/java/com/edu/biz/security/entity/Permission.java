package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Permission  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;

	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}