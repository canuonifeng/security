package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.dao.GradedRankDao;
import com.edu.biz.teaching.dao.GradedSchooltimeDao;
import com.edu.biz.teaching.dao.GradedTeachingDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.GradedSpecification;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class GradedTeachingServiceImpl extends BaseService implements GradedTeachingService {
	@Autowired
	private GradedTeachingDao gradedTeachingDao;
	@Autowired
	private GradedSchooltimeDao gradedSchooletimeDao;
	@Autowired
	private GradedRankDao gradedRankDao;
	@Autowired
	private TermService termService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	
	@Override
	public GradedTeaching createGraded(GradedTeaching graded) {
		Term term = termService.getTermByCurrent(1);
		graded.setTermCode(term.getCode());
		return gradedTeachingDao.save(graded);
	}
	
	@Override
	@Transactional
	public void createSchooltimes(List<GradedSchooltime> list) {
		for (GradedSchooltime time:list) {
			gradedSchooletimeDao.save(time);
		}
	}
	
	@Override
	@Transactional
	public void createRank(List<GradedRank> list) {
		for (GradedRank rank:list) {
			gradedRankDao.save(rank);
		}
	}
	
	@Override
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions) {
		return gradedTeachingDao.findAll(new GradedSpecification(conditions));
	}
	
	@Override
	public GradedTeaching getGradedTeaching(Long id)
	{
		return gradedTeachingDao.findOne(id);
	}
	
	@Override
	public GradedTeaching updateGradedTeaching(GradedTeaching graded) {
		GradedTeaching saveGraded = gradedTeachingDao.findOne(graded.getId());
		if (null == saveGraded) {
			throw new NotFoundException("该教师不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(graded, saveGraded, "course", "schooltime", "classrooms");
		return gradedTeachingDao.save(saveGraded);
	}
	
	@Override
	public List<Classroom> findGradedTeachingClassrooms(Map<String, Object> conditions) {
		Term term = termService.getTermByCurrent(1);
		//判断该课程是否已经排课
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(term.getCode(), Long.parseLong(conditions.get("courseId").toString()));
		if(classSchedule != null) {
			return null;
		}
		//判断该课程是否已经分层
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", conditions.get("courseId"));
		map.put("termCode", term.getCode());
		GradedTeaching gradedTeaching = gradedTeachingDao.findOne(new GradedSpecification(map));
		if(gradedTeaching != null){
			return null;
		}
		//判断该课程是否属于选课
		//TO DO
		map.clear();
		map.put("gradedCourseId", conditions.get("courseId"));
		map.put("currentTermCode",term.getCode());
		map.put("facultyId",conditions.get("facultyId"));
		map.put("grade",conditions.get("grade"));
		return classroomService.findClassrooms(map);
	}
	
	@Override
	public Boolean checkTeachingTime(Map<String, Object> conditions) {
		List<String> periods = getCheckPeriod(conditions);
		List<Long> classroomIds = (List<Long>) conditions.get("classroomIds");
		for (int i = 0; i < classroomIds.size(); i++) {
			for (int j = 0; j < periods.size(); j++) {
				List<ScheduleCycle> scheduleCycles = hasCourseArrange(classroomIds.get(i), periods.get(j), Integer.parseInt(conditions.get("week").toString()));
				if (scheduleCycles.size() != 0) {
					throw new InvalidParameterException("该位置已有课程被排");
				}
			}
		}		
		return true;
	}
	
	@Override
	public Boolean checkTeachingClassroom(Map<String, Object> conditions) {
		// 判断教室在某个星期某节课是否被占用
		List<String> periods = getCheckPeriod(conditions);
		for (int j = 0; j < periods.size(); j++) {
			ScheduleCycle cycle = courseArrangeService.getScheduleCycle(Long.parseLong(conditions.get("buildingRoomId").toString()), periods.get(j), Integer.parseInt(conditions.get("week").toString()));
			if (cycle != null) {
				throw new InvalidParameterException("该教室在此时间被占用");
			}
		}
		return true;
	}

	private List<String> getCheckPeriod(Map<String, Object> conditions) {
		List<String> periods = new ArrayList<>();
		if(conditions.containsKey("morningLesson")) {
			List<String> morningPeriods = (List<String>) conditions.get("morningLesson");
			for (int i = 0; i < morningPeriods.size(); i++) {
				periods.add("1-"+morningPeriods.get(i));
			}
		}
		if(conditions.containsKey("afternoonLesson")) {
			List<String> afternoonPeriods = (List<String>) conditions.get("afternoonLesson");
			for (int i = 0; i < afternoonPeriods.size(); i++) {
				periods.add("1-"+afternoonPeriods.get(i));
			}
		}
		if(conditions.containsKey("nightLesson")) {
			List<String> nightPeriods = (List<String>) conditions.get("nightLesson");
			for (int i = 0; i < nightPeriods.size(); i++) {
				periods.add("1-"+nightPeriods.get(i));
			}
		}
		return periods;
	}
	
	private List<ScheduleCycle> hasCourseArrange(Long classroomId, String period, Integer week) {
		Term term = termService.getTermByCurrent(1);
		HashMap<String, Object> map = new HashMap<>();
		map.put("period", period);
		map.put("week", week);
		map.put("classroomId", classroomId);
		map.put("termCode", term.getCode());
		return courseArrangeService.findScheduleCycles(map);
	}
}
