package com.edu.biz.teachingresources.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingresources.dao.CourseDao;
import com.edu.biz.teachingresources.entity.Course;
import com.edu.biz.teachingresources.entity.CourseStatus;
import com.edu.biz.teachingresources.service.CourseService;
import com.edu.biz.teachingresources.specification.CourseSpecification;
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
			throw new NotFoundException("该教师不存在");
		}
		if (!this.checkCode(course.getCode(), course.getId())) {
			throw new ServiceException("406", "code已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(course, saveCourse, "code", "name");

		return courseDao.save(course);
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
}
