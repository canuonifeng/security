package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedTeaching;

public interface GradedTeachingService {
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);

	public List<Classroom> findGradedTeachingClassrooms(Long courseId);

	public Boolean checkTeachingTime(Map<String, Object> conditions);

	public Boolean checkTeachingClassroom(Map<String, Object> conditions);
}
