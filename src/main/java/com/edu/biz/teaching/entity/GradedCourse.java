package com.edu.biz.teaching.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teachingres.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="graded_course")
public class GradedCourse extends BaseEntity {
	@ManyToOne(targetEntity = GradedTeaching.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_id")
	@ApiModelProperty(value = "分层教学")
	private GradedTeaching gradedTeaching;
	
	@ManyToOne(targetEntity = GradedRank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "rank_id")
	@ApiModelProperty(value = "分层等级")
	private GradedRank gradedRank;
	
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@ApiModelProperty(value = "教师")
	private Teacher teacher;

	@ManyToMany(targetEntity = Student.class, fetch = FetchType.LAZY)
	@JoinTable(name = "graded_classroom_member", joinColumns = @JoinColumn(name = "graded_course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
	@JsonView({ TeachingJsonViews.CascadeStudent.class })
	private List<Student> students;
	
	@ApiModelProperty(value = "学生数")
	private int studentNumber;

	public GradedTeaching getGradedTeaching() {
		return gradedTeaching;
	}

	public void setGradedTeaching(GradedTeaching gradedTeaching) {
		this.gradedTeaching = gradedTeaching;
	}

	public GradedRank getGradedRank() {
		return gradedRank;
	}

	public void setGradedRank(GradedRank gradedRank) {
		this.gradedRank = gradedRank;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
