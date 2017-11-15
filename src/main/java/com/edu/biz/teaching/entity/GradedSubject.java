package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class GradedSubject extends BaseEntity {
	@ApiModelProperty(value = "分层科目名称")
	private String name;
	
	@ApiModelProperty(value = "所属年级")
	private String grade;
	
	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "院系")
	private Faculty faculty;

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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
}
