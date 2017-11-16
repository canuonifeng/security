package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;

public interface GradedTeachingService {
	
	public GradedTeaching createGraded(GradedTeaching graded);
	
	public void createSchooltimes(List<GradedSchooltime> list);
	
	public void createRank(List<GradedRank> list);
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);
	
	public GradedTeaching getGradedTeaching(Long id);
	
	public GradedTeaching updateGradedTeaching(GradedTeaching graded);

	public List<Classroom> findGradedTeachingClassrooms(Long courseId);

	public Boolean checkTeachingTime(Map<String, Object> conditions);

	public Boolean checkTeachingClassroom(Map<String, Object> conditions);
}
