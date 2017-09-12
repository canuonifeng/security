package com.edu.biz.teaching.dao;

import org.springframework.data.jpa.repository.Modifying;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.ScheduleTeacher;

public interface ScheduleTeacherDao extends BaseDao<ScheduleTeacher> {
	@Modifying
	public void deleteByClassScheduleId(Long scheduleId);
}
