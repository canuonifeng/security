package com.edu.biz.teaching.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.teachingres.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name="teaching_program")
public class Program extends BaseEntity {
	private String grade;
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "major_id")
	private Major major;
	
	@OneToMany(targetEntity = Course.class, fetch = FetchType.EAGER)
	@JoinTable(name = "teaching_program_course", joinColumns = @JoinColumn(name = "teaching_program_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	@ApiModelProperty(value = "课程列表")
	private List<Course> courses;
	
	@OneToMany(targetEntity = ProgramCourse.class, fetch = FetchType.EAGER)
	@JoinColumn(name="teaching_program_id", insertable = false, updatable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<ProgramCourse> programCourses;
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<ProgramCourse> getProgramCourses() {
		return programCourses;
	}

	public void setProgramCourses(List<ProgramCourse> programCourses) {
		this.programCourses = programCourses;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
