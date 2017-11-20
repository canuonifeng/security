package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="select_course_schooltime")
public class SelectCourseSchooltime extends BaseEntity {
	@ManyToOne(targetEntity = SelectCourse.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "select_course_id")
	@ApiModelProperty(value = "选课")
	private SelectCourse selectCourse;
	
	@ApiModelProperty(value = "星期几")
	private int week;
	
	@ApiModelProperty(value = "时辰")
	private int timeSlot;
	
	@ApiModelProperty(value = "节")
	private int period;

	public SelectCourse getSelectCourse() {
		return selectCourse;
	}

	public void setSelectCourse(SelectCourse selectCourse) {
		this.selectCourse = selectCourse;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}
