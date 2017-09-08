package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;

public class ScheduleCycleVo extends ScheduleCycle {
	private List<ScheduleTeacher> scheduleTeacher;

	public List<ScheduleTeacher> getScheduleTeacher() {
		return scheduleTeacher;
	}

	public void setScheduleTeacher(List<ScheduleTeacher> scheduleTeacher) {
		this.scheduleTeacher = scheduleTeacher;
	}


}
