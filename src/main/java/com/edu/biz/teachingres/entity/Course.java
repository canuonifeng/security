package com.edu.biz.teachingres.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Course extends BaseEntity {
	
	@NotEmpty(message = "课程名称不能为空")
	@ApiModelProperty(value = " 课程名称")
	private String name;
	
	@NotEmpty(message = "课程编码不能为空")
	@ApiModelProperty(value = " 课程编码")
	private String code;
	
	@NotEmpty(message="学分不能为空")
	@ApiModelProperty(value="学分")
	private String credit;
	
	@ApiModelProperty(value="理论学时")
	private int theoryPeriod;

	@ApiModelProperty(value="实践学时")
	private int practicePeriod;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private CourseStatus status = CourseStatus.enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}

	public int getTheoryPeriod() {
		return theoryPeriod;
	}

	public void setTheoryPeriod(int theoryPeriod) {
		this.theoryPeriod = theoryPeriod;
	}

	public int getPracticePeriod() {
		return practicePeriod;
	}

	public void setPracticePeriod(int practicePeriod) {
		this.practicePeriod = practicePeriod;
	}
	

}
