package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Permission extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String code;

	@ManyToMany(mappedBy = "permissions")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Role> roles;

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
}