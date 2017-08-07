package com.edu.biz.schoolroll.entity.pojo;

public class ClassroomForm {
	private Long majorId;
	private String grade;
	private String classroomPrefix;
	private String classroomSuffix;
	private int num;
	
	public String getClassroomSuffix() {
		return classroomSuffix;
	}
	
	public void setClassroomSuffix(String classroomSuffix) {
		this.classroomSuffix = classroomSuffix;
	}
	
	public String getClassroomPrefix() {
		return classroomPrefix;
	}
	
	public void setClassroomPrefix(String classroomPrefix) {
		this.classroomPrefix = classroomPrefix;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}
}
