package com.edu.biz.security.entity;

import java.util.List;

import javax.persistence.ManyToMany;

import com.edu.biz.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Permission2 extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@XStreamAsAttribute
	@XStreamAlias("name")
	private String name;
	@XStreamAsAttribute
	@XStreamAlias("code")
	private String code;

	@ManyToMany(mappedBy = "permissions")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Role> roles;
	
	private Permission2 parentPermission;
	
	@XStreamImplicit(itemFieldName="permission")	
	private List<Permission2> subpermissions;

	public List<Permission2> getSubpermissions() {
		return subpermissions;
	}

	public void setSubpermissions(List<Permission2> subpermissions) {
		this.subpermissions = subpermissions;
	}

	public Permission2 getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Permission2 parentPermission) {
		this.parentPermission = parentPermission;
	}

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