package com.edu.biz.org.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.security.entity.Organization;

@Entity
public class Faculty extends BaseEntity {

	private String code;
	private String name; 
	
//	@OneToMany(mappedBy="faculty")
//	private Set<Organization> organization;
	
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

//	public Set<Organization> getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(Set<Organization> organization) {
//		this.organization = organization;
//	}
}
