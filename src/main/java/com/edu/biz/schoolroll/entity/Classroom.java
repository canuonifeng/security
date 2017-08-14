package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Classroom extends BaseEntity {
	private String code;
	private String name;
	private String grade;
	private int isAssignNum;
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	@ApiModelProperty(value = "专业")
	private Major major;
	
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public int getIsAssignNum() {
		return isAssignNum;
	}

	public void setIsAssignNum(int isAssignNum) {
		this.isAssignNum = isAssignNum;
	}
}
