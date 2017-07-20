package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Role extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<User> users;
 
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "role_permission", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName="id"), 
        inverseJoinColumns = @JoinColumn(
          name = "permission_id", referencedColumnName="id"))
    private List<Permission> permissions;

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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}  
}
