package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.ProgramCourse;

public interface ProgramCourseDao extends BaseDao<ProgramCourse> {
	public ProgramCourse getByProgramIdAndCourseId(Long programId, Long courseId);
}
