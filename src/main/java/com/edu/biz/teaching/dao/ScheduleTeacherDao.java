package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.ScheduleTeacher;

public interface ScheduleTeacherDao extends BaseDao<ScheduleTeacher> {
	public void deleteByClassScheduleId(Long scheduleId);
}
