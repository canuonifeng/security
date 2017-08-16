package com.edu.biz.teachingres.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.dict.Gender;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Teacher extends BaseEntity {
	@ApiModelProperty(value = " 学号")
	private String no;
	
	@NotEmpty(message = "姓名不能为空")
	@ApiModelProperty(value = " 姓名")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 性别")
	private Gender gender = Gender.secret;
	
	@ApiModelProperty(value="从教时间")
	private String start_work_time;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private TeacherStatus status = TeacherStatus.enable;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGenderName() {
		return gender.getName();
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getStart_work_time() {
		return start_work_time;
	}

	public void setStart_work_time(String start_work_time) {
		this.start_work_time = start_work_time;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public TeacherStatus getStatus() {
		return status;
	}

	public void setStatus(TeacherStatus status) {
		this.status = status;
	}
}
