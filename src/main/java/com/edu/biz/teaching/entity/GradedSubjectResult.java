package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Student;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="student_graded_subject_result")
public class GradedSubjectResult extends BaseEntity {
	
	@ApiModelProperty(value = "分数")
	private Double score;
	
	@ManyToOne(targetEntity = GradedSubject.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_subject_id")
	@ApiModelProperty(value = "所属分层科目")
	private GradedSubject gradedSubject;

	@OneToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	@ApiModelProperty(value = "学生")
	private Student student;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public GradedSubject getGradedSubject() {
		return gradedSubject;
	}

	public void setGradedSubject(GradedSubject gradedSubject) {
		this.gradedSubject = gradedSubject;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
