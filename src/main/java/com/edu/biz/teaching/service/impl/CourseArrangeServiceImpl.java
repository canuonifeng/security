package com.edu.biz.teaching.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.common.dao.service.SettingService;
import com.edu.biz.common.entity.Setting;
import com.edu.biz.teaching.dao.ClassScheduleDao;
import com.edu.biz.teaching.dao.ScheduleCycleDao;
import com.edu.biz.teaching.dao.ScheduleTeacherDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.ScheduleCycleVo;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.ClassScheduleSpecification;
import com.edu.biz.teaching.specification.ScheduleCycleSpecification;
import com.edu.biz.teaching.specification.ScheduleTeacherSpecification;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;
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
	private ScheduleTeacherDao scheduleTeacherDao;
	@Autowired
	private SettingService settingService;
	@Autowired
	private TermService termService;
	@Autowired
	private GradedTeachingService gradedTeachingService;
	@Autowired
	private SelectCourseService selectCourseService;
	@Autowired
	private BuildingService buildingService;

	@Override
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule) {
		return classScheduleDao.save(classSchedule);
	}

	@Override
	public ClassSchedule updateClassSchedule(ClassSchedule classSchedule) {
		ClassSchedule savedClassSchedule = classScheduleDao.findOne(classSchedule.getId());
		if (null == savedClassSchedule) {
			throw new NotFoundException("该排课不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(classSchedule, savedClassSchedule, "classrooms");
		return classScheduleDao.save(savedClassSchedule);
	}

	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("term", term);
		conditions.put("classroomId", classroomId);
		conditions.put("courseId", couresId);
		return classScheduleDao.findOne(new ClassScheduleSpecification(conditions));
	}

	public ClassSchedule getClassSchedule(String term, Long couresId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("term", term);
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

	@Override
	public List<BuildingRoom> findBuildingRooms(Long cycleId) {
		ScheduleCycle scheduleCycle = getScheduleCycle(cycleId);
		return dealCycleBuildingRoom(scheduleCycle);
	}

	private List<BuildingRoom> dealCycleBuildingRoom(ScheduleCycle scheduleCycle) {
		Term term = termService.getTermByCurrent(1);
		List<Long> notBuildingroomIds = new ArrayList<Long>();
		Map<String, Object> map = new HashMap<>();
		// 获取排课同位置教室
		map.put("currentTermCode", term.getCode());
		map.put("period", scheduleCycle.getPeriod());
		List<ScheduleCycle> scheduleCycles = findScheduleCycles(map);
		for (ScheduleCycle cycle : scheduleCycles) {
			if (cycle.getId().equals(scheduleCycle.getId())) {
				continue;
			}
			if (cycle.getBuildingRoom() != null) {
				notBuildingroomIds.add(cycle.getBuildingRoom().getId());
			}
		}

		String sourceTime[] = scheduleCycle.getPeriod().split("-");
		int timeSlot = Integer.parseInt(sourceTime[0]);
		int period = Integer.parseInt(sourceTime[1]);
		map.clear();
		map.put("timeSlot", timeSlot);
		map.put("period", period);
		map.put("week", scheduleCycle.getWeek());
		map.put("currentTermCode", term.getCode());

		// 获取分层同位置教室
		List<GradedCourseSchooltime> gradedCourseSchooltimes = gradedTeachingService.findSchooltimes(map);
		for (GradedCourseSchooltime gradedCourseSchooltime : gradedCourseSchooltimes) {
			if (gradedCourseSchooltime.getBuildingRoom() != null) {
				notBuildingroomIds.add(gradedCourseSchooltime.getBuildingRoom().getId());
			}
		}

		// 获取选课同位置教室
		List<SelectCourseClassSchooltime> selectCourseSchooltimes = selectCourseService.findClassSchooltimes(map);
		for (SelectCourseClassSchooltime selectCourseSchooltime : selectCourseSchooltimes) {
			if (selectCourseSchooltime.getBuildingRoom() != null) {
				notBuildingroomIds.add(selectCourseSchooltime.getBuildingRoom().getId());
			}
		}
		map.clear();
		map.put("notBuildingRoomIds", notBuildingroomIds);
		return buildingService.findAllrooms(map);
	}

	public Map<Integer, Map<String, Object>> getCourseArrange(String term, Long classroomId) {
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

		HashMap<Integer, Map<String, Object>> result = initScheduleData(limit);
		// 排课数据添加
		for (ClassSchedule classSchedule : classSchedules) {
			for (ScheduleCycle scheduleCycle : classSchedule.getScheduleCycles()) {
				ScheduleCycleVo scheduleCycleVo = new ScheduleCycleVo();
				BeanUtils.copyPropertiesWithIgnoreProperties(scheduleCycle, scheduleCycleVo);
				HashMap<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("scheduleId", scheduleCycle.getClassSchedule().getId());
				List<ScheduleTeacher> scheduleTeachers = findScheduleTeachers(conditions);
				scheduleCycleVo.setScheduleTeacher(scheduleTeachers);

				result.get(scheduleCycle.getWeek()).put(scheduleCycle.getPeriod(), scheduleCycleVo);
			}
		}
		// 分层数据添加
		Map<String, Object> map = new HashMap<>();
		Term currentTerm = termService.getTermByCurrent(1);
		map.put("classroomId", classroomId);
		map.put("termCode", currentTerm.getCode());
		List<GradedTeaching> gradedTeachings = gradedTeachingService.findGradedTeachings(map);
		for (GradedTeaching gradedTeaching : gradedTeachings) {
			map.clear();
			map.put("gradedId", gradedTeaching.getId());
			List<GradedSchooltime> gradedSchooltimes = gradedTeachingService.findTimes(map);
			for (GradedSchooltime gradedSchooltime : gradedSchooltimes) {
				result.get(gradedSchooltime.getWeek()).put(
						gradedSchooltime.getTimeSlot() + "-" + gradedSchooltime.getPeriod(),
						gradedTeaching.getCourse());
			}
		}
		return result;
	}

	private HashMap<Integer, Map<String, Object>> initScheduleData(Map<String, Object> limit) {

		HashMap<Integer, Map<String, Object>> result = new HashMap<>();
		for (int i = 1; i <= 7; i++) {
			Map<String, Object> weekSchedules = new LinkedHashMap<>();
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
			result.put(i, weekSchedules);
		}
		return result;
	}

	@Override
	public List<ScheduleTeacher> findScheduleTeachers(Map<String, Object> conditions) {
		return scheduleTeacherDao.findAll(new ScheduleTeacherSpecification(conditions));
	}

	@Override
	public ScheduleCycle getScheduleCycle(Long buildingRoomId, String period, int week) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("buildingRoomId", buildingRoomId);
		conditions.put("period", period);
		conditions.put("week", week);
		return scheduleCycleDao.findOne(new ScheduleCycleSpecification(conditions));
	}

	@Override
	public ScheduleTeacher createScheduleTeacher(ScheduleTeacher scheduleTeacher) {
		return scheduleTeacherDao.save(scheduleTeacher);
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
	public ScheduleCycle getScheduleCycle(Map<String, Object> conditions) {
		return scheduleCycleDao.findOne(new ScheduleCycleSpecification(conditions));
	}

	@Override
	public ClassSchedule getClassSchedule(Long id) {
		return classScheduleDao.findOne(id);
	}

	@Override
	public Long countScheduleCyle(Map<String, Object> map) {
		return scheduleCycleDao.count(new ScheduleCycleSpecification(map));
	}

	@Override
	@Transactional
	public Boolean deleteScheduleTeacherByScheduleId(Long scheduleId) {
		scheduleTeacherDao.deleteByClassScheduleId(scheduleId);
		return true;
	}

	@Override
	public ScheduleTeacher getMasterScheduleTeacher(Long scheduleId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("scheduleId", scheduleId);
		conditions.put("master", 1);
		return scheduleTeacherDao.findOne(new ScheduleTeacherSpecification(conditions));
	}
}
