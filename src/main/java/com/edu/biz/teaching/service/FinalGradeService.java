package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teachingres.entity.Course;

public interface FinalGradeService {

	List<Course> findFinalGradeCourses(Map<String, Object> conditions);
}
