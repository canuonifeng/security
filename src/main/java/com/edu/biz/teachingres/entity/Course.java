package com.edu.biz.teachingres.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.fasterxml.jackson.annotation.JsonView;

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
	
	@ApiModelProperty(value="周学时")
	private int weekPeriod;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private CourseStatus status = CourseStatus.enable;
	
	@ManyToMany(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
	@ApiModelProperty(value = "授课教师")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	private List<Teacher> teachers;

	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "所属院系")
	private Faculty faculty;
	
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public int getWeekPeriod() {
		return weekPeriod;
	}

	public void setWeekPeriod(int weekPeriod) {
		this.weekPeriod = weekPeriod;
	}
	

}
