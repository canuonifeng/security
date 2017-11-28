package com.edu.biz.exam.entity;

import com.edu.biz.org.entity.Faculty;

public class ExamAboutFacultyAndGrade {
	
	private Faculty faculty;
	
	private String grade;
	
	private int classNumber;
	
	private int examNumber;

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public int getExamNumber() {
		return examNumber;
	}

	public void setExamNumber(int examNumber) {
		this.examNumber = examNumber;
	}
}
