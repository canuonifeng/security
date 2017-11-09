package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;
import com.edu.biz.teaching.entity.pojo.ScheduleCycleVo;

public interface CourseArrangeService {
	
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule);
	
	public ClassSchedule updateClassSchedule(ClassSchedule classSchedule);
	
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle);
	
	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId);
	
	public ClassSchedule getClassSchedule(String term, Long couresId);
	
	public Map<Integer, Map<String, ScheduleCycleVo>> getCourseArrange(String term, Long classroomId);

	public Boolean deleteScheduleCycle(Long id);

	public ScheduleCycle getScheduleCycle(Long id);

	public List<ScheduleCycle> findScheduleCycles(Map<String, Object> map);

	public Boolean deleteClassSchedule(Long id);

	public ClassSchedule getClassSchedule(Long id);

	public Long countScheduleCyle(Map<String, Object> map);

	public ScheduleTeacher createScheduleTeacher(ScheduleTeacher scheduleTeacher);

	public List<ScheduleTeacher> findScheduleTeachers(Map<String, Object> conditions);

	public Boolean deleteScheduleTeacherByScheduleId(Long ScheduleId);

	public ScheduleCycle getScheduleCycle(Long buildingRoomId, String period, int week);

	public ScheduleTeacher getMasterScheduleTeacher(Long scheduleId);
}
