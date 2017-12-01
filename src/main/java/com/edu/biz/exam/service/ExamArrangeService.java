package com.edu.biz.exam.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.teachingres.entity.Course;

public interface ExamArrangeService {
	
	public void createExamArrange(ExamArrange examArrange);
	
	public List<ExamArrange> findExamArranges(Map<String, Object> conditions);

	public List<ExamAboutFacultyAndGradeAndTestWay> getExamList(Map<String, Object> conditions);

	public List<Course> findExamArrangeCourses(Map<String, Object> conditions);
	
	public List<ExamArrange> findClassroomExamArranges(Map<String, Object> conditions);

	public ExamArrange getExamArrange(Map<String, Object> map);

	public ExamArrange updateExamArrange(ExamArrange examArrange);
}
