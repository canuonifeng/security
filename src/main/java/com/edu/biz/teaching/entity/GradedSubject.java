package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Major;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class GradedSubject extends BaseEntity {
	@ApiModelProperty(value = "分层科目名称")
	private String name;
	
	@ApiModelProperty(value = "所属年级")
	private String grade;
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	@ApiModelProperty(value = "专业")
	private Major major;

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
}
