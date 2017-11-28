package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="select_course_class")
public class SelectCourseClass extends BaseEntity {
	@ManyToOne(targetEntity = SelectCourse.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "select_course_id")
	@ApiModelProperty(value = "选课")
	@JsonView({ TeachingJsonViews.CascadeSelectCourse.class })	
	private SelectCourse selectCourse;
	
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@ApiModelProperty(value = "教师")
	private Teacher teacher;
	
	@ApiModelProperty(value = "学生数")
	private int studentNumber;

	public SelectCourse getSelectCourse() {
		return selectCourse;
	}

	public void setSelectCourse(SelectCourse selectCourse) {
		this.selectCourse = selectCourse;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}
}
