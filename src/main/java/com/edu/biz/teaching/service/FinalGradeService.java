package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teaching.entity.FinalGradePart;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseForm;
import com.edu.biz.teachingres.entity.Course;

public interface FinalGradeService {

	List<Course> findFinalGradeCourses(Map<String, Object> conditions);

	Boolean setFinalGradePartCourses(FinalGradeCourseForm finalGradeCourseForm);

	FinalGradePart getFinalGradePart(long id);

	FinalGradePartCourse saveFinalGradePartCourse(FinalGradePartCourse finalGradePartCourse);

	List<FinalGradePartCourse> findFinalGradePartCourses(Map<String, Object> map);

	List<FinalGradePart> findFinalGradeParts(Map<String, Object> conditions);
}
