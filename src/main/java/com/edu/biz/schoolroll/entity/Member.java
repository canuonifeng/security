package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

@Entity
@Table(name="classroom_member")
public class Member extends BaseEntity {
	private Long classroomId;
	private Long studentId;
	
	public Long getClassroomId() {
		return classroomId;
	}
	
	public void setClassroomId(Long classroomId) {
		this.classroomId = classroomId;
	}
	
	public Long getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
