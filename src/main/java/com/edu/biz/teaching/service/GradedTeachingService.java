package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;
import com.edu.biz.teachingres.entity.BuildingRoom;

public interface GradedTeachingService {
	
	public GradedTeaching createGraded(GradedTeaching graded);
	
	public void createSchooltimes(List<GradedSchooltime> list);
	
	public void createRank(List<GradedRank> list);
	
	public void createCourse(List<GradedCourseAndCourseTime> list);
	
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions);
	
	public GradedTeaching getGradedTeaching(Long id);
	
	public List<GradedRank> findRanks(Map<String, Object> conditions);
	
	public List<GradedSchooltime> findTimes(Map<String, Object> conditions);
	
	public List<GradedCourseAndCourseTime> findCourses(Map<String, Object> conditions);
	
	public GradedTeaching updateGradedTeaching(GradedTeaching graded);

	public List<Classroom> findGradedTeachingClassrooms(Map<String, Object> conditions);

	public Boolean checkTeachingTime(GradedTimeCheckForm gradedTimeCheckForm);

	public Boolean checkTeachingTeacher(Long id, Long teacherId);

	public Map<String, List<BuildingRoom>> findWeekBuildingRoom(Long id);
}
