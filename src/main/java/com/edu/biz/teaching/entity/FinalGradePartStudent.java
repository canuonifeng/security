package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Student;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="final_grade_part_student")
public class FinalGradePartStudent extends BaseEntity {
	@ManyToOne(targetEntity = FinalGradePartCourse.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "final_grade_part_course_id")
	private FinalGradePartCourse finalGradePartCourse;
	
	@ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	@ApiModelProperty(value = "学生")
	private Student student;

	@ApiModelProperty(value = "学生分数")
	private Double score;

	public FinalGradePartCourse getFinalGradePartCourse() {
		return finalGradePartCourse;
	}

	public void setFinalGradePartCourse(FinalGradePartCourse finalGradePartCourse) {
		this.finalGradePartCourse = finalGradePartCourse;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
}
