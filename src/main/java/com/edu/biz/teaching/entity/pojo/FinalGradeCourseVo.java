package com.edu.biz.teaching.entity.pojo;

import com.edu.biz.teachingres.entity.Course;

public class FinalGradeCourseVo extends Course {
	
	private Integer classroomCount;

	public Integer getClassroomCount() {
		return classroomCount;
	}

	public void setClassroomCount(Integer classroomCount) {
		this.classroomCount = classroomCount;
	}
}
