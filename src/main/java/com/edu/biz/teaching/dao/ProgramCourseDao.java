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
	
	@Query(value="select COUNT(distinct course_id) from ProgramCourse pc where pc.program.major.faculty.id = :facultyId and pc.program.grade = :grade and pc.testWay = :testWay")
	public int countWrittenProgramCourses(@Param("grade")String grade, @Param("facultyId")Long facultyId, @Param("testWay")String testWay);
}
