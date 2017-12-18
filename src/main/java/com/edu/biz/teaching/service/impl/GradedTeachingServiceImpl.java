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
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.dao.GradedCourseDao;
import com.edu.biz.teaching.dao.GradedCourseSchooltimeDao;
import com.edu.biz.teaching.dao.GradedRankDao;
import com.edu.biz.teaching.dao.GradedSchooltimeDao;
import com.edu.biz.teaching.dao.GradedTeachingDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.GradedCourse;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.GradedCourseSchooltimeSpecification;
import com.edu.biz.teaching.specification.GradedCourseSpecification;
import com.edu.biz.teaching.specification.GradedRankSpecification;
import com.edu.biz.teaching.specification.GradedSchooltimeSpecification;
import com.edu.biz.teaching.specification.GradedSpecification;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.biz.teachingres.service.TeacherService;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class GradedTeachingServiceImpl extends BaseService implements GradedTeachingService {
	@Autowired
	private GradedTeachingDao gradedTeachingDao;
	@Autowired
	private GradedSchooltimeDao gradedSchooltimeDao;
	@Autowired
	private GradedRankDao gradedRankDao;
	@Autowired
	private GradedCourseDao gradedCourseDao;
	@Autowired
	private GradedCourseSchooltimeDao gradedCourseSchooltimeDao;
	@Autowired
	private TermService termService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private BuildingService buildingService;

	@Override
	public GradedTeaching createGraded(GradedTeaching graded) {
		Term term = termService.getTermByCurrent(1);
		graded.setTermCode(term.getCode());
		return gradedTeachingDao.save(graded);
	}

	@Override
	@Transactional
	public void createSchooltimes(List<GradedSchooltime> list) {
		for (GradedSchooltime time : list) {
			gradedSchooltimeDao.save(time);
		}
	}

	@Override
	public Map<String, List<BuildingRoom>> findWeekBuildingRoom(Long id) {
		Map<String, List<BuildingRoom>> initBuildingRooms = initWeekBuildingRoom();
		return dealWeekBuildingRoom(initBuildingRooms, id);
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

			// 获取分层星期的教室id集合
			for (int i = 0; i < weekPeriods.get(key).size(); i++) {
				String sourceTime[] = weekPeriods.get(key).get(i).split("-");
				int timeSlot = Integer.parseInt(sourceTime[0]);
				int period = Integer.parseInt(sourceTime[1]);
				conditions.clear();
				conditions.put("timeSlot", timeSlot);
				conditions.put("period", period);
				conditions.put("week", key);
				conditions.put("currentTermCode", term.getCode());
				List<GradedCourseSchooltime> gradedCourseSchooltimes = gradedCourseSchooltimeDao
						.findAll(new GradedCourseSchooltimeSpecification(conditions));
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
		map.put("gradedId", id);
		Map<String, List<String>> periods = new HashMap<>();
		List<GradedSchooltime> gradedSchooltimes = gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(map));
		for (GradedSchooltime gradedSchooltime : gradedSchooltimes) {
			if (periods.containsKey(gradedSchooltime.getWeek())) {
				periods.get(gradedSchooltime.getWeek())
						.add(gradedSchooltime.getTimeSlot() + "-" + gradedSchooltime.getPeriod());
			} else {
				List<String> schooltime = new ArrayList<>();
				schooltime.add(gradedSchooltime.getTimeSlot() + "-" + gradedSchooltime.getPeriod());
				periods.put(String.valueOf(gradedSchooltime.getWeek()), schooltime);
			}
		}
		return periods;
	}

	private Map<String, List<BuildingRoom>> initWeekBuildingRoom() {
		Map<String, List<BuildingRoom>> buildingRooms = new HashMap<>();
		for (int i = 1; i <= 7; i++) {
			buildingRooms.put(String.valueOf(i), null);
		}
		return buildingRooms;
	}

	@Override
	@Transactional
	public void createRank(List<GradedRank> list) {
		for (GradedRank rank : list) {
			gradedRankDao.save(rank);
		}
	}

	@Override
	@Transactional
	public void saveCourse(List<GradedCourseAndCourseTime> list) {
		for (GradedCourseAndCourseTime gradedCourseAndCourseTime : list) {
			GradedCourse gradedCourse = gradedCourseDao.save(gradedCourseAndCourseTime.getGradedCourse());
			Map<String, Object> map = new HashMap<>();
			map.put("gradedCourseId", gradedCourse.getId());
			List<GradedCourseSchooltime> times = findSchooltimesByCourseId(map);
			if (!times.isEmpty()) {
				gradedCourseSchooltimeDao.deleteByGradedCourseId(gradedCourse.getId());
			}
			for (GradedCourseSchooltime courseSchooltime : gradedCourseAndCourseTime.getGradedCourseTime()) {
				courseSchooltime.setGradedCourse(gradedCourse);
				gradedCourseSchooltimeDao.save(courseSchooltime);
			}
		}
	}

	@Override
	public List<GradedCourseSchooltime> findSchooltimesByCourseId(Map<String, Object> conditions) {
		return gradedCourseSchooltimeDao.findAll(new GradedCourseSchooltimeSpecification(conditions));
	}

	@Override
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions) {
		return gradedTeachingDao.findAll(new GradedSpecification(conditions));
	}

	@Override
	public GradedTeaching getGradedTeaching(Long id) {
		return gradedTeachingDao.findOne(id);
	}

	@Override
	public List<GradedRank> findRanks(Map<String, Object> conditions) {
		return gradedRankDao.findAll(new GradedRankSpecification(conditions));
	}

	@Override
	public List<GradedSchooltime> findTimes(Map<String, Object> conditions) {
		return gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(conditions));
	}

	@Override
	public List<GradedCourseAndCourseTime> findCourses(Map<String, Object> conditions) {
		List<GradedCourseAndCourseTime> list = new ArrayList<>();
		List<GradedCourse> courses = gradedCourseDao.findAll(new GradedCourseSpecification(conditions));
		for (GradedCourse course : courses) {
			GradedCourseAndCourseTime courseAndCourseTime = new GradedCourseAndCourseTime();
			courseAndCourseTime.setGradedCourse(course);
			Map<String, Object> map = new HashMap<>();
			map.put("gradedCourseId", course.getId());
			List<GradedCourseSchooltime> time = gradedCourseSchooltimeDao
					.findAll(new GradedCourseSchooltimeSpecification(map));
			courseAndCourseTime.setGradedCourseTime(time);
			list.add(courseAndCourseTime);
		}
		return list;
	}

	@Override
	public GradedTeaching updateGradedTeaching(GradedTeaching graded) {
		GradedTeaching saveGraded = gradedTeachingDao.findOne(graded.getId());
		if (null == saveGraded) {
			throw new NotFoundException("该分层教学不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(graded, saveGraded, "course", "schooltime", "classrooms", "subject");
		return gradedTeachingDao.save(saveGraded);
	}

	@Override
	@Transactional
	public void updateGradedTimes(Long id, List<GradedSchooltime> list) {
		GradedTeaching saveGraded = gradedTeachingDao.findOne(id);
		if (null == saveGraded) {
			throw new NotFoundException("该分层教学不存在");
		}
		gradedSchooltimeDao.deleteByGradedTeachingId(id);
		createSchooltimes(list);
	}

	@Override
	@Transactional
	public void updateGradedRanks(Long id, List<GradedRank> list) {
		GradedTeaching saveGraded = gradedTeachingDao.findOne(id);
		if (null == saveGraded) {
			throw new NotFoundException("该分层教学不存在");
		}
		gradedRankDao.deleteByGradedTeachingId(id);
		createRank(list);
	}
	
	@Override
	@Transactional
	public void updateGradedCourse(Long id, List<GradedCourseAndCourseTime> list) {
		GradedTeaching saveGraded = gradedTeachingDao.findOne(id);
		if (null == saveGraded) {
			throw new NotFoundException("该分层教学不存在");
		}
		gradedCourseSchooltimeDao.deleteByGradedCourseGradedTeachingId(id);
		gradedCourseDao.deleteByGradedTeachingId(id);
		saveCourse(list);
	}

	@Override
	public GradedCourse getGradedCourse(Map<String, Object> conditions) {
		return gradedCourseDao.findOne(new GradedCourseSpecification(conditions));
	}

	@Override
	public List<Student> findAddStudents(Map<String, Object> map) {
		GradedCourse gradedCourse = gradedCourseDao.findOne(new GradedCourseSpecification(map));

		Map<String, Object> conditions = new HashMap<>();
		conditions.put("gradedId", gradedCourse.getGradedTeaching().getId());
		List<Student> students = studentService.findStudents(conditions);
		List<Long> notStudentIds = new ArrayList<>();
		for (Student student : students) {
			notStudentIds.add(student.getId());
		}
		GradedTeaching gradedTeaching = gradedTeachingDao.findOne(gradedCourse.getGradedTeaching().getId());
		List<Classroom> classrooms = gradedTeaching.getClassrooms();
		List<Long> classroomIds = new ArrayList<>();
		for (Classroom classroom : classrooms) {
			classroomIds.add(classroom.getId());
		}
		map.remove("rankId");
		map.remove("teacherId");
		map.put("classroomIds", classroomIds);
		map.put("notStudentIds", notStudentIds);
		return studentService.findStudents(map);
	}

	@Override
	public GradedCourse updateGradedCourse(GradedCourse gradedCourse) {
		GradedCourse saveGradedCourse = gradedCourseDao.findOne(gradedCourse.getId());
		if (null == saveGradedCourse) {
			throw new NotFoundException("该分层课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(gradedCourse, saveGradedCourse, "students");
		return gradedCourseDao.save(saveGradedCourse);
	}
	
	@Override
	@Transactional
	public boolean deleteGradedTeaching(Long id) {
		gradedCourseSchooltimeDao.deleteByGradedCourseGradedTeachingId(id);
		gradedSchooltimeDao.deleteByGradedTeachingId(id);
		gradedCourseDao.deleteByGradedTeachingId(id);
		gradedRankDao.deleteByGradedTeachingId(id);
		gradedTeachingDao.delete(id);
		return true;
	}

	@Override
	public List<Classroom> findGradedTeachingClassrooms(Map<String, Object> conditions) {
		Term term = termService.getTermByCurrent(1);
		// 判断该课程是否已经排课
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(term.getCode(),
				Long.parseLong(conditions.get("courseId").toString()));
		if (classSchedule != null) {
			return null;
		}
		// 判断该课程是否已经分层
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", conditions.get("courseId"));
		map.put("termCode", term.getCode());
		GradedTeaching gradedTeaching = gradedTeachingDao.findOne(new GradedSpecification(map));
		if (gradedTeaching != null) {
			return null;
		}
		// 判断该课程是否属于选课
		// TO DO
		map.clear();
		map.put("gradedCourseId", conditions.get("courseId"));
		map.put("currentTermCode", term.getCode());
		map.put("facultyId", conditions.get("facultyId"));
		map.put("grade", conditions.get("grade"));
		return classroomService.findClassrooms(map);
	}

	@Override
	public Boolean checkTeachingTime(GradedTimeCheckForm gradedTimeCheckForm) {
		List<String> periods = getCheckPeriod(gradedTimeCheckForm.getMorningLesson(),
				gradedTimeCheckForm.getAfternoonLesson(), gradedTimeCheckForm.getNightLesson());
		GradedTeaching gradedTeaching = getGradedTeaching(gradedTimeCheckForm.getGradedTeachingId());
		List<Classroom> classrooms = gradedTeaching.getClassrooms();
		for (Classroom classroom : classrooms) {
			for (int j = 0; j < periods.size(); j++) {
				// 该节课要排位置在排课中是否存在
				checkCourseArrangePosition(classroom, periods.get(j), gradedTimeCheckForm.getWeek());
				// 该节课要排位置在分层课程是否存在
				checkGradedTeachingPosition(classroom, periods.get(j), gradedTimeCheckForm.getWeek(),
						gradedTeaching.getSchooltime());
			}
		}
		return true;
	}

	private void checkGradedTeachingPosition(Classroom classroom, String period, Integer week, String schooltime) {
		String source[] = period.split("-");
		Map<String, Object> map = new HashMap<>();
		map.put("week", week);
		map.put("timeSlot", source[0]);
		map.put("period", source[1]);
		map.put("classroomId", classroom.getId());
		List<GradedSchooltime> gradedSchooletimes = gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(map));
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
			GradedTeaching gradedTeaching = getGradedTeaching(gradedSchooltime.getGradedTeaching().getId());
			String time[] = gradedTeaching.getSchooltime().split("-");
			if ((startWeek <= Integer.parseInt(time[0])) && (Integer.parseInt(time[0]) <= endWeek)) {
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
			if ((startWeek <= Integer.parseInt(time[1])) && (Integer.parseInt(time[1]) <= endWeek)) {
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
		}
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

	@Override
	public Boolean checkTeachingTeacher(Long gradedId, Long teacherId) {
		Teacher teacher = teacherService.getTeacher(teacherId);
		Map<String, Object> map = new HashMap<>();
		map.put("gradedId", gradedId);
		List<GradedSchooltime> gradedSchooltimes = gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(map));
		for (int j = 0; j < gradedSchooltimes.size(); j++) {
			// 该老师在该位置排的必修课是否有课要上
			checkCourseArrangeTeacher(
					gradedSchooltimes.get(j).getTimeSlot() + "-" + gradedSchooltimes.get(j).getPeriod(),
					gradedSchooltimes.get(j).getWeek(), teacher);
			// 该老师在该位置排的分层课是否有课要上
			checkGradedTeachingTeacher(gradedSchooltimes.get(j).getPeriod(), gradedSchooltimes.get(j).getTimeSlot(),
					gradedSchooltimes.get(j).getWeek(), teacher);
			// 该老师在该位置排的选修课是否有课要上
			// TO DO
		}
		return true;
	}

	private void checkGradedTeachingTeacher(Integer period, Integer timeSlot, Integer week, Teacher teacher) {
		Term term = termService.getTermByCurrent(1);
		Map<String, Object> map = new HashMap<>();
		map.put("week", week);
		map.put("timeSlot", timeSlot);
		map.put("period", period);
		map.put("currentTermCode", term.getCode());
		map.put("checkGradedTeacherId", teacher.getId());
		GradedSchooltime gradedSchooltime = gradedSchooltimeDao.findOne(new GradedSchooltimeSpecification(map));
		if (gradedSchooltime != null) {
			createCheckTeachingTeacherError(teacher.getName(), week, timeSlot + "-" + period);
		}
	}

	private void checkCourseArrangeTeacher(String period, Integer week, Teacher teacher) {
		Term term = termService.getTermByCurrent(1);
		Map<String, Object> map = new HashMap<>();
		map.put("week", week);
		map.put("period", period);
		map.put("gradedCheckTeacherId", teacher.getId());
		map.put("currentTermCode", term.getCode());
		map.put("master", 1);
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(map);
		if (scheduleCycle != null) {
			createCheckTeachingTeacherError(teacher.getName(), week, period);
		}

	}

	private void createCheckTeachingTeacherError(String teacherName, Integer week, String period) {
		throw new InvalidParameterException(
				"老师" + teacherName + "在周" + week + TermCodeUtil.getLessonByPeriod(period) + "已有课程要上");
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

	private List<ScheduleCycle> hasCourseArrange(Long classroomId, String period, Integer week) {
		Term term = termService.getTermByCurrent(1);
		HashMap<String, Object> map = new HashMap<>();
		map.put("period", period);
		map.put("week", week);
		map.put("classroomId", classroomId);
		map.put("termCode", term.getCode());
		return courseArrangeService.findScheduleCycles(map);
	}

	@Override
	public List<Classroom> findGradedClassrooms(Long rankId, Long teacherId) {
		Map<String, Object> map = new HashMap<>();
		map.put("rankId", rankId);
		map.put("teacherId", teacherId);
		GradedCourse gradedCourse = gradedCourseDao.findOne(new GradedCourseSpecification(map));
		return gradedCourse.getGradedTeaching().getClassrooms();
	}
	
	@Override
	public GradedTeaching getGradedTeaching(Map<String, Object> map) {
		return gradedTeachingDao.findOne(new GradedSpecification(map));
	}
	
	@Override
	public String getCurrentStep(Long id) {
		GradedTeaching gradedTeaching = gradedTeachingDao.findOne(id);
		if (null == gradedTeaching) {
			throw new NotFoundException("该分层教学不存在");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("gradedId", id);
		List<GradedSchooltime> times = findTimes(map);
		if (times.size() == 0) {
			return "time_set";
		}
		List<GradedRank> ranks = findRanks(map);
		if (times.size() > 0 && ranks.size() == 0) {
			return "rank_set";
		}
		
		return "room_set";
	}
}
