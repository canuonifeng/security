package com.edu.biz.teaching.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.common.dao.service.SettingService;
import com.edu.biz.common.entity.Setting;
import com.edu.biz.teaching.dao.ClassScheduleDao;
import com.edu.biz.teaching.dao.ScheduleCycleDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.specification.ClassScheduleSpecification;
import com.edu.biz.teaching.specification.ScheduleCycleSpecification;
import com.edu.core.exception.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CourseArrangeServiceImpl extends BaseService implements CourseArrangeService {
	@Autowired
	private ClassScheduleDao classScheduleDao;

	@Autowired
	private ScheduleCycleDao scheduleCycleDao;

	@Autowired
	private SettingService settingService;

	@Override
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule) {
		return classScheduleDao.save(classSchedule);
	}

	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("term", term);
		conditions.put("classroomId", classroomId);
		conditions.put("courseId", couresId);
		return classScheduleDao.findOne(new ClassScheduleSpecification(conditions));
	}

	public List<ClassSchedule> findClassSchedules(String term, Long classroomId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("term", term);
		conditions.put("classroomId", classroomId);
		return classScheduleDao.findAll(new ClassScheduleSpecification(conditions));
	}

	@Override
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle) {
		return scheduleCycleDao.save(scheduleCycle);
	}

	public Map<Integer, Map<String, ScheduleCycle>> getCourseArrange(String term, Long classroomId) {
		HashMap<Integer, Map<String, ScheduleCycle>> map = new HashMap<>();
		Setting setting = settingService.getSettingByCode("course_arrange_limit");

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> limit = new HashMap<String, Object>();

		try {
			limit = mapper.readValue(setting.getValue(), new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new ServiceException("json生成异常");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new ServiceException("json映射异常");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("I/O异常");
		}

		List<ClassSchedule> classSchedules = findClassSchedules(term, classroomId);

		for (int i = 1; i <= 7; i++) {
			Map<String, ScheduleCycle> weekSchedules = new LinkedHashMap<>();
			for (int courseNum = 1, prefix = 1; courseNum <= Integer
					.parseInt(limit.get("morning").toString()); courseNum++) {
				weekSchedules.put(prefix + "-" + courseNum, null);
			}
			for (int courseNum = 1, prefix = 2; courseNum <= Integer
					.parseInt(limit.get("afternoon").toString()); courseNum++) {
				weekSchedules.put(prefix + "-" + courseNum, null);
			}
			for (int courseNum = 1, prefix = 3; courseNum <= Integer
					.parseInt(limit.get("night").toString()); courseNum++) {
				weekSchedules.put(prefix + "-" + courseNum, null);
			}
			map.put(i, weekSchedules);
		}
		for (ClassSchedule classSchedule : classSchedules) {
			for (ScheduleCycle scheduleCycle : classSchedule.getScheduleCycles()) {
				map.get(scheduleCycle.getWeek()).put(scheduleCycle.getPeriod(), scheduleCycle);
			}
		}
		return map;
	}

	@Override
	public Boolean deleteScheduleCycle(Long id) {
		scheduleCycleDao.delete(id);
		return null == scheduleCycleDao.findOne(id);
	}

	@Override
	public ScheduleCycle getScheduleCycle(Long id) {
		return scheduleCycleDao.findOne(id);
	}

	@Override
	public List<ScheduleCycle> findScheduleCycles(Map<String, Object> map) {
		return scheduleCycleDao.findAll(new ScheduleCycleSpecification(map));
	}

	@Override
	public Boolean deleteClassSchedule(Long id) {
		classScheduleDao.delete(id);
		return null == classScheduleDao.findOne(id);
	}
	
	@Override
	public ClassSchedule getClassSchedule(Long id) {
		return classScheduleDao.findOne(id);
	}
}
