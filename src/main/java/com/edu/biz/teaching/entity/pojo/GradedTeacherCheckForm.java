package com.edu.biz.teaching.entity.pojo;

import java.util.List;

public class GradedTeacherCheckForm {
	private List<String> morningLesson;
	private List<String> afternoonLesson;
	private List<String> nightLesson;
	private Long teacherId;
	private Integer week;

	public List<String> getMorningLesson() {
		return morningLesson;
	}

	public void setMorningLesson(List<String> morningLesson) {
		this.morningLesson = morningLesson;
	}

	public List<String> getAfternoonLesson() {
		return afternoonLesson;
	}

	public void setAfternoonLesson(List<String> afternoonLesson) {
		this.afternoonLesson = afternoonLesson;
	}

	public List<String> getNightLesson() {
		return nightLesson;
	}

	public void setNightLesson(List<String> nightLesson) {
		this.nightLesson = nightLesson;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
}
