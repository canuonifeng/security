package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teaching.dao.ProgramCourseDao;
import com.edu.biz.teaching.dao.ProgramDao;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.specification.ProgramCourseSpecification;
import com.edu.biz.teaching.specification.ProgramSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.specification.CourseSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class ProgramServiceImpl extends BaseService implements ProgramService {
	@Autowired
	private ProgramDao programDao;
	@Autowired
	private ProgramCourseDao programCourseDao;
	@Autowired
	private CourseDao courseDao;
	@Override
	public Program createProgram(Program program) {
		return programDao.save(program);
	}
	
	@Override
	public ProgramCourse createProgramCourse(ProgramCourse programCourse) {
		return programCourseDao.save(programCourse);
	}

	@Override
	public Program updateProgram(Program program) {
		Program saveProgram = programDao.findOne(program.getId());
		if (null == saveProgram) {
			throw new NotFoundException("该教师不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(program, saveProgram, "no", "name", "gender", "status",
				"start_work_time");

		return programDao.save(saveProgram);
	}

	@Override
	public Boolean deleteProgram(Long id) {
		programDao.delete(id);
		return null == programDao.findOne(id);
	}
	
	@Override
	public Boolean deleteProgramCourse(Long programCourseId) {
		programCourseDao.delete(programCourseId);
		return null == programCourseDao.findOne(programCourseId);
	}
	
	@Override
	public Program getProgram(Long id) {
		return programDao.findOne(id);
	}

	@Override
	public Page<Program> searchPrograms(Map<String, Object> conditions, Pageable pageable) {
		return programDao.findAll(new ProgramSpecification(conditions), pageable);
	}

	@Override
	public Page<ProgramCourse> searchProgramCourse(Map<String, Object> conditions, Pageable pageable) {
		return programCourseDao.findAll(new ProgramCourseSpecification(conditions), pageable);
	}

	@Override
	public Page<Course> searchCoursesNotInProgram(Long programId, Map<String, Object> conditions, Pageable pageable) {
		List<ProgramCourse> existCourses = new ArrayList<ProgramCourse>();
		existCourses  = programCourseDao.findAll(new ProgramCourseSpecification(conditions));
//		Long[] notCourseIds = new Long[existCourses.size()];
		List<Long> notCourseIds = new ArrayList<>();
		for (int i = 0; i < existCourses.size(); i++) {
			notCourseIds.add(existCourses.get(i).getCourse().getId()) ;
		}
		conditions.put("notCourseIds", notCourseIds);
		return courseDao.findAll(new CourseSpecification(conditions), pageable);
	}
	@Override
	public Boolean joinProgram(Course course, Program program) {
		Boolean result = this.canJoinProgram(course, program);
		if (!result) {
			throw new ServiceException("403", "该课程不能加入该教学计划");
		}
		ProgramCourse programCourse = new ProgramCourse();
		programCourse.setCourse(course);
		programCourse.setProgram(program);
		programCourse.setCredit(course.getCredit());
		programCourse.setPeriod(course.getPracticePeriod()+course.getTheoryPeriod());
		createProgramCourse(programCourse);
		return true;
	}

	private Boolean canJoinProgram(Course course, Program program) {
		if (course == null) {
			return false;
		}
		if (program == null) {
			return false;
		}
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("programId", program.getId());
		map.put("courseId", course.getId());
		ProgramCourse programCourse = programCourseDao.getByProgramIdAndCourseId(program.getId(), course.getId());
		if(programCourse == null) {
			return true;
		}
		return false;
	}
}
