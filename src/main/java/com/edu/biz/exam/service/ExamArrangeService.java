package com.edu.biz.exam.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.exam.entity.ExamArrange;

public interface ExamArrangeService {
	
	public void createExamArrange(ExamArrange examArrange);
	
	public List<ExamArrange> findExamArranges(Map<String, Object> conditions);
}
