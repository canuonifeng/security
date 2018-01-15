package com.codeages.security.biz.org.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.codeages.framework.base.BaseEntity;
import com.codeages.security.biz.org.entity.OrgJsonViews.CascadeChildren;
import com.codeages.security.biz.org.entity.OrgJsonViews.CascadeChildrenAndParent;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Organization extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonView({ CascadeChildren.class, CascadeChildrenAndParent.class })
	private List<Organization> children;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonView({ OrgJsonViews.CascadeParent.class, CascadeChildrenAndParent.class })
	private Organization parent;

	private String name;
	private String code;
	private String orgCode;

	public List<Organization> getChildren() {
		if (null != this.children && this.children.size() == 0) {
			return null;
		}
		return children;
	}

	public void setChildren(List<Organization> children) {
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
