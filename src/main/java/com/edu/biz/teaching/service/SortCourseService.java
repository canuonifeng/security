package com.edu.biz.teaching.service;

import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleClassroom;
import com.edu.biz.teaching.entity.ScheduleCycle;

public interface SortCourseService {
	
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule);
	
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle);
	
	public ScheduleClassroom createScheduleClassroom(ScheduleClassroom scheduleCalssroom);
	
	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId);
}
