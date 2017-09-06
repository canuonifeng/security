package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;

public interface CourseArrangeService {
	
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule);
	
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle);
	
	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId);
	
	public Map<Integer, Map<String, ScheduleCycle>> getCourseArrange(String term, Long classroomId);

	public Boolean deleteScheduleCycle(Long id);

	public ScheduleCycle getScheduleCycle(Long id);

	public List<ScheduleCycle> findScheduleCycles(Map<String, Object> map);

	public Boolean deleteClassSchedule(Long id);

	public ClassSchedule getClassSchedule(Long id);
}
