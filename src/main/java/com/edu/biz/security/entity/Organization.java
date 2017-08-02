package com.edu.biz.security.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.viewgroup.JsonViews;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Organization extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@OneToMany(cascade=CascadeType.REMOVE)
	@JoinColumn(name = "parent_id")
	@JsonView({OrgJsonViews.AscadeChildren.class,OrgJsonViews.NoAscadeChildren.class})
	private Set<Organization> children;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonView({OrgJsonViews.AscadeParent.class,OrgJsonViews.NoAscadeParent.class})
	private Organization parent;

	private String name;
	private String code;

	@ManyToOne
	@JoinColumn(name="faculty_id")
	@JsonView({JsonViews.Ascade.class,JsonViews.NoAscade.class})
	private Faculty faculty;
	
	
	public Set<Organization> getChildren() {
		if(null != this.children && this.children.size()==0) {
			return null;
		}
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
