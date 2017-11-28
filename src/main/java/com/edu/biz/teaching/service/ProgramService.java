package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teaching.entity.CountProgramCourseCategory;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teachingres.entity.Course;

public interface ProgramService {
	public Program createProgram(Program program);
	
	public Program updateProgram(Program program);
	
	public Boolean deleteProgram(Long id);
	
	public Program getProgram(Long id);
	
	public Page<Program> searchPrograms(Map<String, Object> conditions, Pageable pageable);

	public Page<ProgramCourse> searchProgramCourse(Map<String, Object> conditions, Pageable pageable);
	
	public List<ProgramCourse> findProgramCourse(Map<String, Object> conditions);

	public Page<Course> searchCoursesNotInProgram(Long programId, Map<String, Object> conditions, Pageable pageable);

	public Boolean joinProgram(Course course, Program program);
	
	public ProgramCourse createProgramCourse(ProgramCourse programCourse);

	public ProgramCourse updateProgramCourse(ProgramCourse programCourse);
	
	public Boolean deleteProgramCourse(Long programCourseId);

	public Map<String, Map<String, List<ProgramCourse>>> showCourseTable(Long id);

	public List<Term> getProgramTerm(Long id);

	public List<ProgramCourse> searchAllProgramCourse(Map<String, Object> conditions);

	public ProgramCourse getProgramCourse(long courseId, String term);

	Program getProgram(String grade, Long majorId);
	
	public List<CountProgramCourseCategory> countProgramCourseByProgramIdGroupByCategory(Long programId);

}
