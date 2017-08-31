package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Classroom;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="schedule_calssroom")
public class ScheduleClassroom extends BaseEntity {
	
	@ManyToOne(targetEntity = ClassSchedule.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	@ApiModelProperty(value = "课表")
	private ClassSchedule classSchedule;
	
	@ManyToOne(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "classroom_id")
	@ApiModelProperty(value = "教室")
	private Classroom classroom;

	public ClassSchedule getClassSchedule() {
		return classSchedule;
	}

	public void setClassSchedule(ClassSchedule classSchedule) {
		this.classSchedule = classSchedule;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
}
