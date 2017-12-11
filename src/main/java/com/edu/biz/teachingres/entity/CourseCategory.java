package com.edu.biz.teachingres.entity;

public enum CourseCategory {
	//课程分类
	pubLiteracy("公共素养课"), pubLiteracyExpand("公共素养拓展"), professionalSupport("专业支撑课"), professionalCore(
			"专业核心课程"), professionalExpand("专业拓展课"), comprehensivePractice("综合实践");

	private String name;

	private CourseCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
