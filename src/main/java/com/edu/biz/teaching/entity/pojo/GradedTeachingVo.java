package com.edu.biz.teaching.entity.pojo;

import java.util.List;

import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;

public class GradedTeachingVo extends GradedTeaching {
	
	private List<GradedSchooltime> gradedSchooltimes;

	public List<GradedSchooltime> getGradedSchooltimes() {
		return gradedSchooltimes;
	}

	public void setGradedSchooltimes(List<GradedSchooltime> gradedSchooltimes) {
		this.gradedSchooltimes = gradedSchooltimes;
	}
}
