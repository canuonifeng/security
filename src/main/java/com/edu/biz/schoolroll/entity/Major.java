package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Major extends BaseEntity {
	private String code;
	private String name;
	
	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "院系")
	private Faculty faculty;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private MajorStatus status;
	
	public MajorStatus getStatus() {
		return status;
	}

	public void setStatus(MajorStatus status) {
		this.status = status;
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
}
