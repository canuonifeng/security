package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;

public interface GradedTeachingService {
	
	public GradedTeaching createGraded(GradedTeaching graded);
	
	public void createSchooltimes(List<GradedSchooltime> list);
	
	public void createRank(List<GradedRank> list);
	
	public void saveCourse(List<GradedCourseAndCourseTime> list);
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);
	
	public GradedTeaching getGradedTeaching(Long id);
	
	public List<GradedRank> findRanks(Map<String, Object> conditions);
	
	public List<GradedSchooltime> findTimes(Map<String, Object> conditions);
	
	public List<GradedCourseAndCourseTime> findCourses(Map<String, Object> conditions);
	
	public GradedTeaching updateGradedTeaching(GradedTeaching graded);
	
	public void updateGradedTimes(Long id, List<GradedSchooltime> list);
	
	public void updateGradedRanks(Long id, List<GradedRank> list);
	
	public void updateGradedCourse(Long id, List<GradedCourseAndCourseTime> list);

	public List<Classroom> findGradedTeachingClassrooms(Map<String, Object> conditions);

	public Boolean checkTeachingTime(GradedTimeCheckForm gradedTimeCheckForm);

	public Boolean checkTeachingClassroom(Map<String, Object> conditions);

	public Boolean checkTeachingTeacher(Long id, Long teacherId);

	public List<GradedCourseSchooltime> findSchooltimesByCourseId(Map<String, Object> conditions);
}
