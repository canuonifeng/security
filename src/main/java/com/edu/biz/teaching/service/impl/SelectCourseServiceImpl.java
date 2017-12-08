package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.common.util.TermCodeUtil;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.dao.SelectCourseClassDao;
import com.edu.biz.teaching.dao.SelectCourseClassSchooltimeDao;
import com.edu.biz.teaching.dao.SelectCourseDao;
import com.edu.biz.teaching.dao.SelectCourseSchooltimeDao;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClass;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.SelectCourseTimeCheckForm;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.SelectCourseClassSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseClassSpecification;
import com.edu.biz.teaching.specification.SelectCourseSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseSpecification;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class SelectCourseServiceImpl extends BaseService implements SelectCourseService {
	@Autowired
	private SelectCourseDao selectCourseDao;
	@Autowired
	private SelectCourseSchooltimeDao selectCourseSchooltimeDao;
	@Autowired
	private SelectCourseClassDao selectCourseClassDao;
	@Autowired
	private SelectCourseClassSchooltimeDao selectCourseClassSchooltimeDao;
	@Autowired
	private TermService termService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	@Autowired
	private GradedTeachingService gradedTeachingService;
	@Autowired
	private BuildingService buildingService;
	@Autowired
	private SelectCourseService selectCourseService;

	@Override
	public List<SelectCourse> findSelectCourses(Map<String, Object> conditions) {
		return selectCourseDao.findAll(new SelectCourseSpecification(conditions));
	}

	@Override
	public SelectCourse createSelectCourse(SelectCourse selectCourse) {
		Term term = termService.getTermByCurrent(1);
		selectCourse.setTermCode(term.getCode());
		return selectCourseDao.save(selectCourse);
	}

	@Override
	public void createSchooltimes(List<SelectCourseSchooltime> list) {
		for (SelectCourseSchooltime time : list) {
			selectCourseSchooltimeDao.save(time);
		}
	}

	@Override
	public void saveClass(List<SelectCourseClassAndClassSchooltime> list) {
		for (SelectCourseClassAndClassSchooltime selectCourseClassAndClassCourse : list) {
			SelectCourseClass selectCourseClass = selectCourseClassDao
					.save(selectCourseClassAndClassCourse.getSelectCourseClass());
			Map<String, Object> map = new HashMap<>();
			map.put("selectCourseClassId", selectCourseClass.getId());
			List<SelectCourseClassSchooltime> times = findClassSchooltimes(map);
			if (!times.isEmpty()) {
				selectCourseClassSchooltimeDao.deleteBySelectCourseClassId(selectCourseClass.getId());
			}
			for (SelectCourseClassSchooltime classSchooltime : selectCourseClassAndClassCourse
					.getSelectCourseClassSchooltimes()) {
				classSchooltime.setSelectCourseClass(selectCourseClass);
				selectCourseClassSchooltimeDao.save(classSchooltime);
			}
		}
	}

	@Override
	public List<SelectCourseClass> findSelectCourseClasses(Map<String, Object> map) {
		return selectCourseClassDao.findAll(new SelectCourseClassSpecification(map));
	}

	@Override
	public List<SelectCourseClassSchooltime> findClassSchooltimes(Map<String, Object> conditions) {
		return selectCourseClassSchooltimeDao.findAll(new SelectCourseClassSchooltimeSpecification(conditions));
	}

	@Override
	public SelectCourse getSelectCourse(Long id) {
		return selectCourseDao.findOne(id);
	}

	@Override
	public List<SelectCourseSchooltime> findTimes(Map<String, Object> conditions) {
		return selectCourseSchooltimeDao.findAll(new SelectCourseSchooltimeSpecification(conditions));
	}

	@Override
	public Boolean checkSelectCourseTime(SelectCourseTimeCheckForm selectCourseTimeCheckForm) {
		List<String> periods = getCheckPeriod(selectCourseTimeCheckForm.getMorningLesson(),
				selectCourseTimeCheckForm.getAfternoonLesson(), selectCourseTimeCheckForm.getNightLesson());
		SelectCourse selectCourse = getSelectCourse(selectCourseTimeCheckForm.getSelectCourseId());
		List<Classroom> classrooms = selectCourse.getClassrooms();
		for (Classroom classroom : classrooms) {
			for (int j = 0; j < periods.size(); j++) {
				// 该节课要排位置在排课中是否存在
				checkCourseArrangePosition(classroom, periods.get(j), selectCourseTimeCheckForm.getWeek());
				// 该节课要排位置在分层课程是否存在
				checkGradedTeachingPosition(classroom, periods.get(j), selectCourseTimeCheckForm.getWeek(),
						selectCourse.getSchooltime());
			}
		}
		return true;
	}

	@Override
	public List<SelectCourseClassAndClassSchooltime> findClass(Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("selectCourseId", id);
		List<SelectCourseClassAndClassSchooltime> list = new ArrayList<>();
		List<SelectCourseClass> selectCourseClasses = selectCourseClassDao
				.findAll(new SelectCourseClassSpecification(conditions));
		for (SelectCourseClass selectCourseClass : selectCourseClasses) {
			SelectCourseClassAndClassSchooltime classAndClassSchooltime = new SelectCourseClassAndClassSchooltime();
			classAndClassSchooltime.setSelectCourseClass(selectCourseClass);
			Map<String, Object> map = new HashMap<>();
			map.put("selectCourseClassId", selectCourseClass.getId());
			List<SelectCourseClassSchooltime> times = selectCourseClassSchooltimeDao
					.findAll(new SelectCourseClassSchooltimeSpecification(map));
			classAndClassSchooltime.setSelectCourseClassSchooltimes(times);
			;
			list.add(classAndClassSchooltime);
		}
		return list;
	}

	@Override
	public Map<String, List<BuildingRoom>> findWeekBuildingRoom(Long id) {
		Map<String, List<BuildingRoom>> initBuildingRooms = initWeekBuildingRoom();
		return dealWeekBuildingRoom(initBuildingRooms, id);
	}

	private Map<String, List<BuildingRoom>> initWeekBuildingRoom() {
		Map<String, List<BuildingRoom>> buildingRooms = new HashMap<>();
		for (int i = 1; i <= 7; i++) {
			buildingRooms.put(String.valueOf(i), null);
		}
		return buildingRooms;
	}

	private Map<String, List<BuildingRoom>> dealWeekBuildingRoom(Map<String, List<BuildingRoom>> initBuildingRooms,
			Long id) {
		Map<String, List<Long>> notBuildingRoomIds = new HashMap<>();
		Map<String, List<String>> weekPeriods = getWeekPeriods(id);
		Term term = termService.getTermByCurrent(1);
		Map<String, Object> conditions = new HashMap<>();
		for (String key : weekPeriods.keySet()) {
			// 获取排课星期的教室id集合
			conditions.clear();
			conditions.put("periods", weekPeriods.get(key));
			conditions.put("currentTermCode", term.getCode());
			List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(conditions);
			for (ScheduleCycle scheduleCycle : scheduleCycles) {
				if (scheduleCycle.getBuildingRoom() != null) {
					if (notBuildingRoomIds.containsKey(key)) {
						notBuildingRoomIds.get(key).add(scheduleCycle.getBuildingRoom().getId());
					} else {
						List<Long> classroomIds = new ArrayList<>();
						classroomIds.add(scheduleCycle.getBuildingRoom().getId());
						notBuildingRoomIds.put(key, classroomIds);
					}
				}
			}

			// 获取分层星期的教室id集合(未判断周是否重合)
			for (int i = 0; i < weekPeriods.get(key).size(); i++) {
				String sourceTime[] = weekPeriods.get(key).get(i).split("-");
				int timeSlot = Integer.parseInt(sourceTime[0]);
				int period = Integer.parseInt(sourceTime[1]);
				conditions.clear();
				conditions.put("timeSlot", timeSlot);
				conditions.put("period", period);
				conditions.put("week", key);
				conditions.put("currentTermCode", term.getCode());
				List<GradedCourseSchooltime> gradedCourseSchooltimes = gradedTeachingService
						.findSchooltimes(conditions);
				for (GradedCourseSchooltime gradedCourseSchooltime : gradedCourseSchooltimes) {
					if (gradedCourseSchooltime.getBuildingRoom() != null) {
						if (notBuildingRoomIds.containsKey(key)) {
							notBuildingRoomIds.get(key).add(gradedCourseSchooltime.getBuildingRoom().getId());
						} else {
							List<Long> classroomIds = new ArrayList<>();
							classroomIds.add(gradedCourseSchooltime.getBuildingRoom().getId());
							notBuildingRoomIds.put(key, classroomIds);
						}
					}
				}
			}

			// 获取选课星期的教室id集合(未判断周是否重合)
			for (int i = 0; i < weekPeriods.get(key).size(); i++) {
				String sourceTime[] = weekPeriods.get(key).get(i).split("-");
				int timeSlot = Integer.parseInt(sourceTime[0]);
				int period = Integer.parseInt(sourceTime[1]);
				conditions.clear();
				conditions.put("timeSlot", timeSlot);
				conditions.put("period", period);
				conditions.put("week", key);
				conditions.put("currentTermCode", term.getCode());
				List<SelectCourseClassSchooltime> selectCourseSchooltimes = selectCourseService
						.findClassSchooltimes(conditions);
				for (SelectCourseClassSchooltime selectCourseSchooltime : selectCourseSchooltimes) {
					if (selectCourseSchooltime.getBuildingRoom() != null) {
						if (notBuildingRoomIds.containsKey(key)) {
							notBuildingRoomIds.get(key).add(selectCourseSchooltime.getBuildingRoom().getId());
						} else {
							List<Long> classroomIds = new ArrayList<>();
							classroomIds.add(selectCourseSchooltime.getBuildingRoom().getId());
							notBuildingRoomIds.put(key, classroomIds);
						}
					}
				}
			}
		}
		for (String week : initBuildingRooms.keySet()) {
			conditions.clear();
			conditions.put("notBuildingRoomIds", notBuildingRoomIds.get(week));
			List<BuildingRoom> rooms = buildingService.findAllrooms(conditions);
			initBuildingRooms.put(week, rooms);
		}
		return initBuildingRooms;
	}

	private Map<String, List<String>> getWeekPeriods(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("selectCourseId", id);
		Map<String, List<String>> periods = new HashMap<>();
		List<SelectCourseSchooltime> selectCourseSchooltimes = findTimes(map);
		for (SelectCourseSchooltime selectCourseSchooltime : selectCourseSchooltimes) {
			if (periods.containsKey(String.valueOf(selectCourseSchooltime.getWeek()))) {
				periods.get(String.valueOf(selectCourseSchooltime.getWeek()))
						.add(selectCourseSchooltime.getTimeSlot() + "-" + selectCourseSchooltime.getPeriod());
			} else {
				List<String> schooltime = new ArrayList<>();
				schooltime.add(selectCourseSchooltime.getTimeSlot() + "-" + selectCourseSchooltime.getPeriod());
				periods.put(String.valueOf(selectCourseSchooltime.getWeek()), schooltime);
			}
		}
		return periods;
	}

	@Override
	@Transactional
	public void updateSchooltimes(Long id, List<SelectCourseSchooltime> list) {
		SelectCourse saveSelectCourse = selectCourseDao.findOne(id);
		if (null == saveSelectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		selectCourseSchooltimeDao.deleteBySelectCourseId(id);
		createSchooltimes(list);
	}

	@Override
	public SelectCourse updateSelectCourse(SelectCourse selectCourse) {
		SelectCourse saveSelectCourse = selectCourseDao.findOne(selectCourse.getId());
		if (null == saveSelectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(selectCourse, saveSelectCourse, "course", "schooltime",
				"classrooms");
		return selectCourseDao.save(saveSelectCourse);
	}

	@Override
	public List<Classroom> findSelectCourseClassrooms(Map<String, Object> conditions) {
		Term term = termService.getTermByCurrent(1);
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", conditions.get("courseId"));
		map.put("termCode", term.getCode());
		List<SelectCourse> selectCourses = findSelectCourses(map);

		List<Classroom> notClassrooms = new ArrayList<>();
		if (selectCourses.size() != 0) {
			for (SelectCourse selectCourse : selectCourses) {
				if (conditions.containsKey("selectCourseId")
						&& selectCourse.getId().equals(conditions.get("selectCourseId"))) {
					continue;
				}
				List<Classroom> classrooms = selectCourse.getClassrooms();
				notClassrooms.addAll(classrooms);
			}
		}
		List<Long> notClassroomIds = new ArrayList<>();
		for (int i = 0; i < notClassrooms.size(); i++) {
			notClassroomIds.add(notClassrooms.get(i).getId());
		}
		conditions.put("selectCourseId", conditions.get("courseId"));
		conditions.put("currentTermCode", term.getCode());
		conditions.put("notClassroomIds", notClassroomIds);
		conditions.put("nature", "elective");
		return classroomService.findClassrooms(conditions);
	}

	@Transactional
	@Override
	public void updateSelectCourseClass(Long id, List<SelectCourseClassAndClassSchooltime> list) {
		SelectCourse selectCourse = selectCourseDao.findOne(id);
		if (null == selectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		selectCourseClassSchooltimeDao.deleteBySelectCourseClassSelectCourseId(id);
		selectCourseClassDao.deleteBySelectCourseId(id);
		saveClass(list);
	}

	@Override
	@Transactional
	public boolean deleteSelectCourse(Long id) {
		selectCourseClassSchooltimeDao.deleteBySelectCourseClassSelectCourseId(id);
		selectCourseSchooltimeDao.deleteBySelectCourseId(id);
		selectCourseClassDao.deleteBySelectCourseId(id);
		selectCourseDao.delete(id);
		return true;
	}

	private List<String> getCheckPeriod(List<String> morningLesson, List<String> afternoonLesson,
			List<String> nightLesson) {
		List<String> periods = new ArrayList<>();
		if (morningLesson != null) {
			for (int i = 0; i < morningLesson.size(); i++) {
				periods.add("1-" + morningLesson.get(i));
			}
		}
		if (afternoonLesson != null) {
			for (int i = 0; i < afternoonLesson.size(); i++) {
				periods.add("1-" + afternoonLesson.get(i));
			}
		}
		if (nightLesson != null) {
			for (int i = 0; i < nightLesson.size(); i++) {
				periods.add("1-" + nightLesson.get(i));
			}
		}
		return periods;
	}

	private void checkCourseArrangePosition(Classroom classroom, String period, Integer week) {
		List<ScheduleCycle> scheduleCycles = hasCourseArrange(classroom.getId(), period, week);
		if (scheduleCycles.size() != 0) {
			createCheckTeachingTimeError(classroom.getName(), week, period);
		}
	}

	private void createCheckTeachingTimeError(String classroomName, Integer week, String period) {
		throw new InvalidParameterException(
				"班级" + classroomName + "在周" + week + TermCodeUtil.getLessonByPeriod(period) + "已有课程被排");
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

	private void checkGradedTeachingPosition(Classroom classroom, String period, Integer week, String schooltime) {
		String source[] = period.split("-");
		Map<String, Object> map = new HashMap<>();
		map.put("week", week);
		map.put("timeSlot", source[0]);
		map.put("period", source[1]);
		map.put("classroomId", classroom.getId());
		List<GradedSchooltime> gradedSchooletimes = gradedTeachingService.findTimes(map);
		if (gradedSchooletimes.size() > 0) {
			checkTimeWeekIsCoincide(classroom, gradedSchooletimes, schooltime, period, week);
		}
	}

	private void checkTimeWeekIsCoincide(Classroom classroom, List<GradedSchooltime> gradedSchooletimes,
			String schooltime, String period, Integer week) {
		String sourceTime[] = schooltime.split("-");
		int startWeek = Integer.parseInt(sourceTime[0]);
		int endWeek = Integer.parseInt(sourceTime[1]);
		for (GradedSchooltime gradedSchooltime : gradedSchooletimes) {
			GradedTeaching gradedTeaching = gradedTeachingService
					.getGradedTeaching(gradedSchooltime.getGradedTeaching().getId());
			String time[] = gradedTeaching.getSchooltime().split("-");
			if ((startWeek <= Integer.parseInt(time[0])) && (Integer.parseInt(time[0]) <= endWeek)) {
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
			if ((startWeek <= Integer.parseInt(time[1])) && (Integer.parseInt(time[1]) <= endWeek)) {
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
		}
	}
}
