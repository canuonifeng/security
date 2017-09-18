package com.edu.biz.teaching.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teachingres.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="class_schedule")
public class ClassSchedule extends BaseEntity {
	private String term;
	
	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@ApiModelProperty(value = "课程")
	private Course course;
	
	@ManyToMany(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinTable(name = "schedule_calssroom", joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"))
	private List<Classroom> classrooms;

	@OneToMany(targetEntity = ScheduleCycle.class, fetch = FetchType.LAZY)
	@JoinColumn(name="schedule_id", insertable = false, updatable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<ScheduleCycle> scheduleCycles;
	
	@OneToMany(cascade = CascadeType.REMOVE, targetEntity = ScheduleTeacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name="schedule_id", insertable = false, updatable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<ScheduleTeacher> scheduleTeachers;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<Classroom> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public List<ScheduleCycle> getScheduleCycles() {
		return scheduleCycles;
	}

	public void setScheduleCycles(List<ScheduleCycle> scheduleCycles) {
		this.scheduleCycles = scheduleCycles;
	}

	public List<ScheduleTeacher> getScheduleTeachers() {
		return scheduleTeachers;
	}

	public void setScheduleTeachers(List<ScheduleTeacher> scheduleTeachers) {
		this.scheduleTeachers = scheduleTeachers;
	}
	
}
