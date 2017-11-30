package com.edu.biz.exam.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.teachingres.entity.Course;

public interface ExamArrangeService {
	public void createExamArrange(ExamArrange examArrange);

	public List<ExamAboutFacultyAndGradeAndTestWay> getExamList(Map<String, Object> conditions);

	public List<Course> findExamArrangeCourses(Map<String, Object> conditions);
}
