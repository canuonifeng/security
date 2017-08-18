package com.edu.biz.teaching.entity;

import javax.persistence.Entity;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Term extends BaseEntity {
	@ApiModelProperty(value = "标题")
	private String title;
	
	@ApiModelProperty(value = "短代码")
	private String code;
	
	@ApiModelProperty(value = "长代码")
	private String longCode;
	
	@ApiModelProperty(value = "学期开始时间")
	private String startDate;
	
	@ApiModelProperty(value = "学期结束时间")
	private String endDate;
	
	@ApiModelProperty(value = "是否为当前学期")
	private int current;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLongCode() {
		return longCode;
	}

	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
}
