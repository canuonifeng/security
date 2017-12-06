package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.teachingres.entity.Course;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="final_grade_part_course")
public class FinalGradePartCourse extends BaseEntity {
	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "所属院系")
	private Faculty faculty;
	
	@ManyToOne(targetEntity = FinalGradePart.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "final_grade_part_id")
	@ApiModelProperty(value = "成绩组成")
	private FinalGradePart finalGradePart;
	
	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@ApiModelProperty(value = "课程")
	private Course course;
	
	@ApiModelProperty(value = "学期编码")
	private String termCode;

	@ApiModelProperty(value = "分数")
	private Double score;

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public FinalGradePart getFinalGradePart() {
		return finalGradePart;
	}

	public void setFinalGradePart(FinalGradePart finalGradePart) {
		this.finalGradePart = finalGradePart;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
