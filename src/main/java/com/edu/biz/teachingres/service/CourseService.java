package com.edu.biz.teachingres.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.CourseStatus;

public interface CourseService {
	
	public Course createCourse(Course course);
	
	public Course updateCourse(Course course);
	
	public Course changeCourseStatus(Long id, CourseStatus status);
	
	public Boolean deleteCourse(Long id);
	
	public Course getCourse(Long id);
	
	public Page<Course> searchCourses(Map<String, Object> conditions, Pageable pageable);

	public Boolean checkCode(String code, Long courseId);
}
