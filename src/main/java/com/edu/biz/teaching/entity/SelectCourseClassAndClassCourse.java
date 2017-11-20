package com.edu.biz.teaching.entity;

import java.util.List;

public class SelectCourseClassAndClassCourse {
	
	private SelectCourseClass selectCourseClass;
	
	private List<SelectCourseClassSchooltime> selectCourseClassSchooltimes;

	public List<SelectCourseClassSchooltime> getSelectCourseClassSchooltimes() {
		return selectCourseClassSchooltimes;
	}

	public void setSelectCourseClassSchooltimes(List<SelectCourseClassSchooltime> selectCourseClassSchooltimes) {
		this.selectCourseClassSchooltimes = selectCourseClassSchooltimes;
	}

	public SelectCourseClass getSelectCourseClass() {
		return selectCourseClass;
	}

	public void setSelectCourseClass(SelectCourseClass selectCourseClass) {
		this.selectCourseClass = selectCourseClass;
	}
}
