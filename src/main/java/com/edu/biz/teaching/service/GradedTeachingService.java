package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.pojo.GradedTeacherCheckForm;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;

public interface GradedTeachingService {
	
	public GradedTeaching createGraded(GradedTeaching graded);
	
	public void createSchooltimes(List<GradedSchooltime> list);
	
	public void createRank(List<GradedRank> list);
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);
	
	public GradedTeaching getGradedTeaching(Long id);
	
	public GradedTeaching updateGradedTeaching(GradedTeaching graded);

	public List<Classroom> findGradedTeachingClassrooms(Map<String, Object> conditions);

	public Boolean checkTeachingTime(GradedTimeCheckForm gradedTimeCheckForm);

	public Boolean checkTeachingClassroom(Map<String, Object> conditions);

	public Boolean checkTeachingTeacher(GradedTeacherCheckForm gradedTeacherCheckForm);
}
