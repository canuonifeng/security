package com.edu.biz.teaching.entity;

import java.util.List;

public class GradedCourseAndCourseTime {
	
	private GradedCourse gradedCourse;
	
	private List<GradedCourseSchooltime> gradedCourseTime;

	public GradedCourse getGradedCourse() {
		return gradedCourse;
	}

	public void setGradedCourse(GradedCourse gradedCourse) {
		this.gradedCourse = gradedCourse;
	}

	public List<GradedCourseSchooltime> getGradedCourseTime() {
		return gradedCourseTime;
	}

	public void setGradedCourseTime(List<GradedCourseSchooltime> gradedCourseTime) {
		this.gradedCourseTime = gradedCourseTime;
	}
}
