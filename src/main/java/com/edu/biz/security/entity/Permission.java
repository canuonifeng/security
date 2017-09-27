package com.edu.biz.security.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "permission")
@XmlAccessorType (XmlAccessType.FIELD)
public class Permission {

	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "code")
	private String code;

	
	private Permission parentPermission;
	
	@XmlElement(name = "permission")
	private List<Permission> subpermissions;

	public List<Permission> getSubpermissions() {
		return subpermissions;
	}

	public void setSubpermissions(List<Permission> subpermissions) {
		this.subpermissions = subpermissions;
	}

	public Permission getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Permission parentPermission) {
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