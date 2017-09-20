package com.edu.biz.teaching.entity;


public class CountProgramCourseCategory {
	private String category;
	private Long count;

	public CountProgramCourseCategory(String category, Long count) {
		this.setCategory(category);
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
