package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;

@Entity
public class Role extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users;
 
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", 
		joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") , 
		inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id") )
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
