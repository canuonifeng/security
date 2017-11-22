package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;

public interface SelectCourseService {
	
	public List<SelectCourse> findSelectCourses(Map<String, Object> conditions);
	
	public SelectCourse createSelectCourse(SelectCourse selectCourse);
	
	public void createSchooltimes(List<SelectCourseSchooltime> list);
	
	public void saveClass(List<SelectCourseClassAndClassSchooltime> list);
	
	public List<SelectCourseClassSchooltime> findSchooltimesByClassId(Map<String, Object> conditions);
	
	public SelectCourse getSelectCourse(Long id);
	
	public SelectCourse updateSelectCourse(SelectCourse selectCourse);
	
	public void updateSchooltimes(Long id, List<SelectCourseSchooltime> list);
	
	public void updateSelectCourseClass(Long id, List<SelectCourseClassAndClassSchooltime> list);
	
	public List<SelectCourseSchooltime> findTimes(Map<String, Object> conditions);

	public List<Classroom> findSelectCourseClassrooms(Map<String, Object> conditions);
	
	public List<SelectCourseClassAndClassSchooltime> findClass(Long id);
}
