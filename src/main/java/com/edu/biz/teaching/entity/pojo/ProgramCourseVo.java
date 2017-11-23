package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.ProgramCourse;

public class ProgramCourseVo extends ProgramCourse {
	
	private Integer remainderCourseNum;
	private List<Classroom> mergeClassroom;
	private Boolean isGradedCourse;
	
	public Integer getRemainderCourseNum() {
		return remainderCourseNum;
	}

	public void setRemainderCourseNum(Integer remainderCourseNum) {
		this.remainderCourseNum = remainderCourseNum;
	}

	public List<Classroom> getMergeClassroom() {
		return mergeClassroom;
	}

	public void setMergeClassroom(List<Classroom> mergeClassroom) {
		this.mergeClassroom = mergeClassroom;
	}

	public Boolean getIsGradedCourse() {
		return isGradedCourse;
	}

	public void setIsGradedCourse(Boolean isGradedCourse) {
		this.isGradedCourse = isGradedCourse;
	}
}
