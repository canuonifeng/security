package com.edu.biz.teachingres.entity.pojo;

import java.sql.Timestamp;

import com.edu.biz.teachingres.entity.Course;

public class CourseVo extends Course {
	private Timestamp examStartTime;
	private Timestamp examEndTime;

	public Timestamp getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(Timestamp examStartTime) {
		this.examStartTime = examStartTime;
	}

	public Timestamp getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(Timestamp examEndTime) {
		this.examEndTime = examEndTime;
	}
}
