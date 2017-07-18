package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Permission  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;

	@ManyToMany
	@JoinTable(name="role_permission",joinColumns=@JoinColumn(name="permission_id", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
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