package com.edu.biz.teaching.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.service.FinalGradeService;
import com.edu.biz.teaching.specification.FinalGradeSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;

@Service
public class FinalGradeServiceImpl extends BaseService implements FinalGradeService {
	@Autowired
	private CourseDao courseDao;
	@Override
	public List<Course> findFinalGradeCourses(Map<String, Object> conditions) {
		return courseDao.findAll(new FinalGradeSpecification(conditions));
	}
	
}
