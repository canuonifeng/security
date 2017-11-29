package com.edu.biz.teachingres.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.specification.ExamCourseSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.CourseStatus;
import com.edu.biz.teachingres.service.CourseService;
import com.edu.biz.teachingres.specification.CourseSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class CourseServiceImpl extends BaseService implements CourseService {
	@Autowired
	private CourseDao courseDao;

	@Override
	public Course createCourse(Course course) {
		return courseDao.save(course);
	}

	@Override
	public Course updateCourse(Course course) {
		Course saveCourse = courseDao.findOne(course.getId());
		if (null == saveCourse) {
			throw new NotFoundException("该课程不存在");
		}
		if (!this.checkCode(course.getCode(), course.getId())) {
			throw new ServiceException("406", "code已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(course, saveCourse, "code", "name", "credit", "period", "weekPeriod", "faculty");

		return courseDao.save(course);
	}
	
	@Override
	public Course giveTeachers(Course course) {
		Course saveCourse = courseDao.findOne(course.getId());
		if (null == saveCourse) {
			throw new NotFoundException("该课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(course, saveCourse, "teachers");

		return courseDao.save(saveCourse);
	}

	@Override
	public Course changeCourseStatus(Long id, CourseStatus status) {
		Course savedCourse = courseDao.findOne(id);
		if (null == savedCourse) {
			throw new NotFoundException("该教师不存在");
		}
		savedCourse.setStatus(status);
		return courseDao.save(savedCourse);
	}

	@Override
	public Boolean deleteCourse(Long id) {
		courseDao.delete(id);
		return null == courseDao.findOne(id);
	}

	@Override
	public Course getCourse(Long id) {
		return courseDao.findOne(id);
	}

	@Override
	public Page<Course> searchCourses(Map<String, Object> conditions, Pageable pageable) {
		return courseDao.findAll(new CourseSpecification(conditions), pageable);
	}
	
	@Override
	public List<Course> findCourses(Map<String, Object> conditions)
	{
		return courseDao.findAll(new CourseSpecification(conditions));
	}

	@Override
	public Boolean checkCode(String code, Long id) {
		Course course = courseDao.getByCode(code);
		if (null == course) {
			return true;
		}
		if (course.getId().equals(id)) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Course> findProgramCourses(Map<String, Object> conditions) {
		return courseDao.findAll(new ExamCourseSpecification(conditions));
	}
}
