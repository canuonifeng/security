package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teachingres.entity.Course;

public class FinalGradeCourseVo extends Course {
	
	private Integer classroomCount;
	private List<FinalGradePartCourse> finalGradePartCourses;

	public Integer getClassroomCount() {
		return classroomCount;
	}

	public void setClassroomCount(Integer classroomCount) {
		this.classroomCount = classroomCount;
	}

	public List<FinalGradePartCourse> getFinalGradePartCourses() {
		return finalGradePartCourses;
	}

	public void setFinalGradePartCourses(List<FinalGradePartCourse> finalGradePartCourses) {
		this.finalGradePartCourses = finalGradePartCourses;
	}
}
