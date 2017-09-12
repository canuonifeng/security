package com.edu.biz.teachingres.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.dict.Gender;
import com.edu.biz.viewgroup.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

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
	private String startWorkTime;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private TeacherStatus status = TeacherStatus.enable;
	
	@ManyToMany(targetEntity = Course.class, fetch = FetchType.EAGER)
	@JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	@ApiModelProperty(value = "所授课程")
	@JsonView({ JsonViews.Cascade.class })
	private List<Course> courses;
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

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

	public String getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
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
