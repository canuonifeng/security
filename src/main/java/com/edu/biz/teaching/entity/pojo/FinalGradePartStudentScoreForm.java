package com.edu.biz.teaching.entity.pojo;

import java.util.Map;

public class FinalGradePartStudentScoreForm {
	private Map<String, String> gradeCourseScore;
	private Long courseId;
	private Long facultyId;
	private double score;
	private Long finalGradePartCourseId;
	private Long studentId;
	private String termCode;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Long getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Long facultyId) {
		this.facultyId = facultyId;
	}

	public Map<String, String> getGradeCourseScore() {
		return gradeCourseScore;
	}

	public void setGradeCourseScore(Map<String, String> gradeCourseScore) {
		this.gradeCourseScore = gradeCourseScore;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Long getFinalGradePartCourseId() {
		return finalGradePartCourseId;
	}

	public void setFinalGradePartCourseId(Long finalGradePartCourseId) {
		this.finalGradePartCourseId = finalGradePartCourseId;
	}
}
