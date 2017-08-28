package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.Course;


@Entity
@Table(name="teaching_program_course")
public class ProgramCourse extends BaseEntity {
	private String category;
	private String nature;
	private String testWay;
	private String credit;
	private int practicePeriod;
	private int theoryPeriod;
	private String termCode;
	private Integer termNum;
	private Integer weekPeriod;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "teaching_program_id")
	private Program program;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTestWay() {
		return testWay;
	}

	public void setTestWay(String testWay) {
		this.testWay = testWay;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public int getPracticePeriod() {
		return practicePeriod;
	}

	public void setPracticePeriod(int practicePeriod) {
		this.practicePeriod = practicePeriod;
	}

	public int getTheoryPeriod() {
		return theoryPeriod;
	}

	public void setTheoryPeriod(int theoryPeriod) {
		this.theoryPeriod = theoryPeriod;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Integer getTermNum() {
		return termNum;
	}

	public void setTermNum(Integer termNum) {
		this.termNum = termNum;
	}

	public Integer getWeekPeriod() {
		return weekPeriod;
	}

	public void setWeekPeriod(Integer weekPeriod) {
		this.weekPeriod = weekPeriod;
	}
}
