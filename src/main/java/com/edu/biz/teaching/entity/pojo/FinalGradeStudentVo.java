package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.FinalGradePartStudent;

public class FinalGradeStudentVo extends Student {
	
	private double finalGradeScore;
	private List<FinalGradePartCourse> finalGradePartCourses;
	private List<FinalGradePartStudent> finalGradePartStudents;

	public double getFinalGradeScore() {
		return finalGradeScore;
	}

	public void setFinalGradeScore(double finalGradeScore) {
		this.finalGradeScore = finalGradeScore;
	}
	
	public List<FinalGradePartCourse> getFinalGradePartCourses() {
		return finalGradePartCourses;
	}

	public void setFinalGradePartCourses(List<FinalGradePartCourse> finalGradePartCourses) {
		this.finalGradePartCourses = finalGradePartCourses;
	}

	public List<FinalGradePartStudent> getFinalGradePartStudents() {
		return finalGradePartStudents;
	}

	public void setFinalGradePartStudents(List<FinalGradePartStudent> finalGradePartStudents) {
		this.finalGradePartStudents = finalGradePartStudents;
	}
}
