package com.edu.biz.exam.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.teachingres.entity.Course;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class ExamArrange extends BaseEntity {
	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@ApiModelProperty(value = "课程")
	private Course course;
	
	private String ExamTime;
	
	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "院系")
	private Faculty faculty;
	
	private String grade;
	
	private String termCode;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getExamTime() {
		return ExamTime;
	}

	public void setExamTime(String examTime) {
		ExamTime = examTime;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
}
