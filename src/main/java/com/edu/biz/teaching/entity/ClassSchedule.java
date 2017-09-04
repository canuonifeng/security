package com.edu.biz.teaching.entity;

import java.util.List;

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

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="class_schedule")
public class ClassSchedule extends BaseEntity {
	private String term;
	
	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@ApiModelProperty(value = "课程")
	private Course course;
	
	@ManyToMany(targetEntity = Classroom.class, fetch = FetchType.EAGER)
	@JoinTable(name = "schedule_calssroom", joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"))
	private List<Classroom> classrooms;

	@OneToMany(targetEntity = ScheduleCycle.class, fetch = FetchType.LAZY)
	@JoinColumn(name="schedule_id")
	private List<ScheduleCycle> scheduleCycles;

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
	
}
