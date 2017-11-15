package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.dao.GradedTeachingDao;
import com.edu.biz.teaching.entity.ClassSchedule;
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
	private TermService termService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	
	@Override
	public GradedTeaching createGraded(GradedTeaching graded) {
		return gradedTeachingDao.save(graded);
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
	public List<Classroom> findGradedTeachingClassrooms(Long courseId) {
		Term term = termService.getTermByCurrent(1);
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(term.getCode(), courseId);
		if(classSchedule != null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("mergeCourseId", courseId);
		map.put("termCode",term.getCode());
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
