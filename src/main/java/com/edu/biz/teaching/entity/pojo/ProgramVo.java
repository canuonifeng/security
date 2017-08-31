package com.edu.biz.teaching.entity.pojo;

import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.Term;

public class ProgramVo extends Program {
	private Long classroomNum;
	private Term term;

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

}
