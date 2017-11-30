package com.edu.biz.exam.entity;

import com.edu.biz.org.entity.Faculty;

public class ExamAboutFacultyAndGradeAndTestWay {
	
	private Faculty faculty;
	
	private String grade;
	
	private int classroomNumber;
	
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

	public int getClassroomNumber() {
		return classroomNumber;
	}

	public void setClassroomNumber(int classroomNumber) {
		this.classroomNumber = classroomNumber;
	}

	public int getExamNumber() {
		return examNumber;
	}

	public void setExamNumber(int examNumber) {
		this.examNumber = examNumber;
	}
}
