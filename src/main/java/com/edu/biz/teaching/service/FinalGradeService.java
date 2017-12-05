package com.edu.biz.teaching.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teaching.entity.FinalGradePart;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.FinalGradePartStudent;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseForm;
import com.edu.biz.teachingres.entity.Course;

public interface FinalGradeService {

	public List<Course> findFinalGradeCourses(Map<String, Object> conditions);

	public Boolean setFinalGradePartCourses(FinalGradeCourseForm finalGradeCourseForm);

	public FinalGradePart getFinalGradePart(long id);

	public FinalGradePartCourse saveFinalGradePartCourse(FinalGradePartCourse finalGradePartCourse);

	public List<FinalGradePartCourse> findFinalGradePartCourses(Map<String, Object> map);

	public List<FinalGradePart> findFinalGradeParts(Map<String, Object> conditions);

	public List<Student> findFinalGradeStudents(Map<String, Object> conditions);

	public List<FinalGradePartStudent> findFinalGradePartStudents(HashMap<String, Object> map);

	public FinalGradePartStudent getFinalGradePartStudent(Map<String, Object> map);

	public FinalGradePartCourse getFinalGradePartCourse(Long finalGradePartCourseId);

	public FinalGradePartStudent createFinalGradePartStudent(FinalGradePartStudent finalGradePartStudent);

	public FinalGradePartStudent updateFinalGradePartStudent(FinalGradePartStudent finalGradePartStudent);
}
