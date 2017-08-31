package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.common.dao.service.SettingService;
import com.edu.biz.common.entity.Setting;
import com.edu.biz.teaching.dao.ClassScheduleDao;
import com.edu.biz.teaching.dao.ScheduleClassroomDao;
import com.edu.biz.teaching.dao.ScheduleCycleDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleClassroom;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.service.SortCourseService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.core.exception.NotFoundException;

@Service
public class SortCourseServiceImpl extends BaseService implements SortCourseService {
	@Autowired
	private ClassScheduleDao classScheduleDao;

	@Autowired
	private ScheduleCycleDao scheduleCycleDao;

	@Autowired
	private ScheduleClassroomDao scheduleClassroomDao;
	
	@Autowired
	private SettingService settingService;

	@Override
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule) {
		return classScheduleDao.save(classSchedule);
	}

	@Override
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle) {
		return scheduleCycleDao.save(scheduleCycle);
	}

	@Override
	public ScheduleClassroom createScheduleClassroom(ScheduleClassroom scheduleCalssroom) {
		return scheduleClassroomDao.save(scheduleCalssroom);
	}

	private Map<Integer, Map<String, ClassSchedule>> getCourseTable(){
		HashMap<Integer, Map<String, ClassSchedule>> map = new HashMap<>();
		Setting setting = settingService.getSettingByCode("sort_course_limit");
		
		List<ClassSchedule> classSchedules = new ArrayList<>();
		
//		for (ClassSchedule classSchedule : classSchedules) {
//			for (ScheduleCycle scheduleCycle : classSchedule.getScheduleCycles()) {
//				if(map.containsKey(scheduleCycle.getWeek())) {
//					map.get(scheduleCycle.getWeek()).put(scheduleCycle.getPeriod(), classSchedule);
//				} else {
//					Map<String, ClassSchedule> result = new HashMap<>();
//					result.put(scheduleCycle.getPeriod(), classSchedule);
//					map.put(scheduleCycle.getWeek(), result);
//				}
//			}
//		}
		
		
		
		
		for (int i = 1; i <= 7; i++) {
			Map<String, ClassSchedule> map1 = new HashMap<>();
			for (int a = 1,  prefix = 1; a <= 4; a++) {
				map1.put(prefix+"-"+a, new ClassSchedule());
			}
			for (int b = 1, prefix = 2; b <= 4; b++) {
				map1.put(prefix+"-"+b, new ClassSchedule());
			}
			for (int c = 1, prefix = 3; c <= 4; c++) {
				map1.put(prefix+"-"+c, new ClassSchedule());
			}
			map.put(i, map1);
		}
		for (ClassSchedule classSchedule : classSchedules) {
			for (ScheduleCycle scheduleCycle : classSchedule.getScheduleCycles()) {
				map.get(scheduleCycle.getWeek()).put(scheduleCycle.getPeriod(), classSchedule);
			}
		}
		
		return null;
	}
}
