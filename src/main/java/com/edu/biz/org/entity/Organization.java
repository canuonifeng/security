package com.edu.biz.org.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.OrgJsonViews.CascadeChildren;
import com.edu.biz.org.entity.OrgJsonViews.CascadeChildrenAndParent;
import com.edu.biz.viewgroup.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Organization extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonView({ CascadeChildren.class, CascadeChildrenAndParent.class })
	private Set<Organization> children;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonView({ OrgJsonViews.CascadeParent.class, CascadeChildrenAndParent.class })
	private Organization parent;

	private String name;
	private String code;
	private String orgCode;

	@ManyToOne
	@JoinColumn(name = "faculty_id")
	@JsonView({ JsonViews.Cascade.class })
	private Faculty faculty;

	public Set<Organization> getChildren() {
		if (null != this.children && this.children.size() == 0) {
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
