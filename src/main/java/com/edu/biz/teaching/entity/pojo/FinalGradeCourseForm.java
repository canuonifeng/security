package com.edu.biz.teaching.entity.pojo;

import java.util.Map;

public class FinalGradeCourseForm {
	private Map<String, String> gradeCourseScore;
	private Long courseId;
	private Long facultyId;
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
}
