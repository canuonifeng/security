package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;

public class SelectCourseVo extends SelectCourse {
	
	private List<SelectCourseSchooltime> selectCourseSchooltimes;

	public List<SelectCourseSchooltime> getSelectCourseSchooltimes() {
		return selectCourseSchooltimes;
	}

	public void setSelectCourseSchooltimes(List<SelectCourseSchooltime> selectCourseSchooltimes) {
		this.selectCourseSchooltimes = selectCourseSchooltimes;
	}
}
