package com.edu.biz.org.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Faculty extends BaseEntity {

	private String code;
	private String name; 
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private FacultyStatus status = FacultyStatus.enable;
	
	public FacultyStatus getStatus() {
		return status;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public void setStatus(FacultyStatus status) {
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
}
