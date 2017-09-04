package com.edu.biz.teaching.service;

import java.util.Map;

import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;

public interface CourseArrangeService {
	
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule);
	
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle);
	
	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId);
	
	public Map<Integer, Map<String, ClassSchedule>> getCourseTable(String term, Long classroomId);
}
