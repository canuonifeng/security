package com.edu.biz.teaching.entity.pojo;

import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.Term;

public class ProgramVo extends Program {
	private Long classroomNum;
	private Term term;
	private Integer publicCourseNum = 0;
	private Integer professionalCourseNum = 0;
	private Integer practiceCourseNum = 0;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Long getClassroomNum() {
		return classroomNum;
	}

	public void setClassroomNum(Long classroomNum) {
		this.classroomNum = classroomNum;
	}

	public Integer getPublicCourseNum() {
		return publicCourseNum;
	}

	public void setPublicCourseNum(Integer publicCourseNum) {
		this.publicCourseNum = publicCourseNum;
	}

	public Integer getProfessionalCourseNum() {
		return professionalCourseNum;
	}

	public void setProfessionalCourseNum(Integer professionalCourseNum) {
		this.professionalCourseNum = professionalCourseNum;
	}

	public Integer getPracticeCourseNum() {
		return practiceCourseNum;
	}

	public void setPracticeCourseNum(Integer practiceCourseNum) {
		this.practiceCourseNum = practiceCourseNum;
	}

}
