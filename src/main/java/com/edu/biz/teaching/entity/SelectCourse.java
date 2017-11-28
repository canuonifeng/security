package com.edu.biz.teaching.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teachingres.entity.Course;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="select_course")
public class SelectCourse extends BaseEntity {
	
	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@ApiModelProperty(value = "课程")
	private Course course;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = SelectCourseClass.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "select_course_id", insertable = false, updatable = false)
	@JsonView({ TeachingJsonViews.CascadeSelectCourseClass.class })
	private List<SelectCourseClass> selectCourseClasses;
	
	@ApiModelProperty(value = "上课时间")
	private String schooltime;
	
	@ApiModelProperty(value = "所属学期")
	private String termCode;
	
	@OneToMany(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinTable(name = "select_course_classroom", joinColumns = @JoinColumn(name = "select_course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"))
	@ApiModelProperty(value = "适用班级")
	private List<Classroom> classrooms;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSchooltime() {
		return schooltime;
	}

	public void setSchooltime(String schooltime) {
		this.schooltime = schooltime;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public List<Classroom> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public List<SelectCourseClass> getSelectCourseClasses() {
		return selectCourseClasses;
	}

	public void setSelectCourseClasses(List<SelectCourseClass> selectCourseClasses) {
		this.selectCourseClasses = selectCourseClasses;
	}
}
