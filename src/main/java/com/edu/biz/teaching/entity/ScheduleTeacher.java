package com.edu.biz.teaching.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.Teacher;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="schedule_teacher")
public class ScheduleTeacher extends BaseEntity {
	private int master;
	
	@ManyToOne(targetEntity = ClassSchedule.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	@ApiModelProperty(value = "排课")
	private ClassSchedule classSchedule;
	
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@ApiModelProperty(value = "老师")
	private Teacher teacher;

	public int getMaster() {
		return master;
	}

	public void setMaster(int master) {
		this.master = master;
	}

	public ClassSchedule getClassSchedule() {
		return classSchedule;
	}

	public void setClassSchedule(ClassSchedule classSchedule) {
		this.classSchedule = classSchedule;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
