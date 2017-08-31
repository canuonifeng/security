package com.edu.biz.teaching.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.dao.ClassScheduleDao;
import com.edu.biz.teaching.dao.ScheduleClassroomDao;
import com.edu.biz.teaching.dao.ScheduleCycleDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleClassroom;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.service.SortCourseService;

@Service
public class SortCourseServiceImpl extends BaseService implements SortCourseService {
	@Autowired
	private ClassScheduleDao classScheduleDao;

	@Autowired
	private ScheduleCycleDao scheduleCycleDao;

	@Autowired
	private ScheduleClassroomDao scheduleClassroomDao;

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
}
