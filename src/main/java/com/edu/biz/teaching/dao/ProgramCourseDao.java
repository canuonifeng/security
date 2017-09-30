package com.edu.biz.teaching.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.CountProgramCourseCategory;
import com.edu.biz.teaching.entity.ProgramCourse;

public interface ProgramCourseDao extends BaseDao<ProgramCourse> {
	public ProgramCourse getByProgramIdAndCourseId(Long programId, Long courseId);
	
	@Query(value = "select new com.edu.biz.teaching.entity.CountProgramCourseCategory(pc.category, count(pc)) from ProgramCourse pc where pc.program.id=:programId group by pc.category")
	public List<CountProgramCourseCategory> countProgramCourseByProgramIdGroupByCategory(@Param("programId") Long programId);
}
