package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="classroom_member")
public class ClassroomMember extends BaseEntity {
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "classroom_id")
	@ApiModelProperty(value = "班级")
	private Classroom classroom;
	private Long studentId;
	
	public Long getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
}
