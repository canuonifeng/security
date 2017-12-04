package com.edu.biz.teaching.entity;

import javax.persistence.Entity;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class FinalGradePart extends BaseEntity {

	@ApiModelProperty(value = "成绩名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
