package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="final_grade_part")
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
