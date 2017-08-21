package com.edu.biz.teachingres.entity;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Building extends BaseEntity {
	@NotEmpty(message = "名称不能为空")
	@ApiModelProperty(value = " 名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
