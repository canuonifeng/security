package com.edu.biz.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;

@Entity
public class Organization extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Organization parent;
	private String name;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
