package com.edu.biz.security.entity;

import javax.persistence.Entity;

import com.edu.biz.base.BaseEntity;

@Entity
public class Permission  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}