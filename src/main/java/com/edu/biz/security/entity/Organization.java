package com.edu.biz.security.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Organization extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@OneToMany
	@JoinColumn(name = "parent_id")
	private Set<Organization> children;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Organization parent;

	private String name;
	private String code;

	@ManyToOne
	@JoinColumn(name="faculty_id")
	private Faculty faculty;
	
	
	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
		this.children = children;
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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}
}
