package com.edu.biz.user.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Role extends BaseEntity{
	
	private String name;
	
    @ManyToMany
    @JoinTable(name="user_role",joinColumns=@JoinColumn(name="role_id"),
    inverseJoinColumns=@JoinColumn(name="user_id"))
    private Collection<User> users;
 
    @ManyToMany
    @JoinTable(
        name = "roles_permission", 
        joinColumns = @JoinColumn(
          name = "role_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "permission_id"))
    private Collection<Permission> permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}  
}
