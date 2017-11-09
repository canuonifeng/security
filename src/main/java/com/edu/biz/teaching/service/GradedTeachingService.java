package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teaching.entity.GradedTeaching;

public interface GradedTeachingService {
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);
}
