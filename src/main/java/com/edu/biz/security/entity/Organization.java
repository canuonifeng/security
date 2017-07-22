package com.edu.biz.security.entity;

import javax.persistence.Entity;

import com.edu.biz.base.BaseEntity;

@Entity
public class Organization extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Organization parent;
	private String name;

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
