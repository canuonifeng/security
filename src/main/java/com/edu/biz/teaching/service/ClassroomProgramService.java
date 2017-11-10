package com.edu.biz.teaching.service;

import java.util.Map;

import com.edu.biz.teaching.entity.ProgramCourse;

public interface ClassroomProgramService {
	public ProgramCourse setProgramCourse(Long programCourseId, Map<String, Object> map);
}
