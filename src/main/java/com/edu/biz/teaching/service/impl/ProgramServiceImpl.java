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
	public Page<Course> searchNotAddCourses(Long programId, Pageable pageable) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("programId", programId);
		List<ProgramCourse> existCourses = new ArrayList<ProgramCourse>();
		existCourses  = programCourseDao.findAll(new ProgramCourseSpecification(map));
		Long[] notCourseIds = new Long[existCourses.size()];
		for (int i = 0; i < existCourses.size(); i++) {
			notCourseIds[i] = existCourses.get(i).getId();
		}
		map.clear();
		map.put("notCourseIds", notCourseIds);
		return courseDao.findAll(new CourseSpecification(map), pageable);
	}
}
