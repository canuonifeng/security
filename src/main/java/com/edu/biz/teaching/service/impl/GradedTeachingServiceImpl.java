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
import com.edu.biz.teaching.specification.GradedRankSpecification;
import com.edu.biz.teaching.specification.GradedSchooltimeSpecification;
import com.edu.biz.teaching.specification.GradedSpecification;
import com.edu.biz.teachingres.entity.Teacher;
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
	@Transactional
	public void createRank(List<GradedRank> list) {
		for (GradedRank rank : list) {
			gradedRankDao.save(rank);
		}
	}

	@Override
	@Transactional
	public void createCourse(List<GradedCourseAndCourseTime> list) {
		for(GradedCourseAndCourseTime gradedCourseAndCourseTime: list) {
			GradedCourse gradedCourse = gradedCourseDao.save(gradedCourseAndCourseTime.getGradedCourse());
			for (GradedCourseSchooltime courseSchooltime: gradedCourseAndCourseTime.getGradedCourseTime()) {
				courseSchooltime.setGradedCourse(gradedCourse);
				gradedCourseSchooltimeDao.save(courseSchooltime);
			}
		}
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
	public List<GradedRank> findRanks(Map<String, Object> conditions){
		return gradedRankDao.findAll(new GradedRankSpecification(conditions));
	}
	
	@Override
	public List<GradedSchooltime> findTimes(Map<String, Object> conditions) {
		return gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(conditions));
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
		List<String> periods = getCheckPeriod(gradedTimeCheckForm.getMorningLesson(), gradedTimeCheckForm.getAfternoonLesson(), gradedTimeCheckForm.getNightLesson());
		GradedTeaching gradedTeaching = getGradedTeaching(gradedTimeCheckForm.getGradedId());
		List<Classroom> classrooms = gradedTeaching.getClassrooms();
		for (Classroom classroom : classrooms) {
			for (int j = 0; j < periods.size(); j++) {
				//该节课要排位置在排课中是否存在
				checkCourseArrangePosition(classroom, periods.get(j), gradedTimeCheckForm.getWeek());
				//该节课要排位置在分层课程是否存在
				checkGradedTeachingPosition(classroom, periods.get(j), gradedTimeCheckForm.getWeek(), gradedTeaching.getSchooltime());
			}
		}
		return true;
	}

	private void checkGradedTeachingPosition(Classroom classroom, String period, Integer week, String schooltime) {
		String source[]=period.split("-");
		Map<String, Object> map = new HashMap<>();
		map.put("week", week);
		map.put("timeSlot", source[0]);
		map.put("period", source[1]);
		map.put("classroomId", classroom.getId());
		List<GradedSchooltime> gradedSchooletimes = gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(map));
		if(gradedSchooletimes.size() > 0){
			checkTimeWeekIsCoincide(classroom, gradedSchooletimes, schooltime, period, week);
		}
	}

	private void checkTimeWeekIsCoincide(Classroom classroom, List<GradedSchooltime> gradedSchooletimes, String schooltime, String period, Integer week) {
		String sourceTime[] = schooltime.split("-");
		int startWeek = Integer.parseInt(sourceTime[0]);
		int endWeek = Integer.parseInt(sourceTime[1]);
		for (GradedSchooltime gradedSchooltime : gradedSchooletimes) {
			GradedTeaching gradedTeaching = getGradedTeaching(gradedSchooltime.getGradedTeaching().getId());
			String time[] = gradedTeaching.getSchooltime().split("-");
			if((startWeek <= Integer.parseInt(time[0])) && (Integer.parseInt(time[0]) <= endWeek)){
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
			if((startWeek <= Integer.parseInt(time[1])) && (Integer.parseInt(time[1]) <= endWeek)){
				createCheckTeachingTimeError(classroom.getName(), week, period);
			}
		}
	}

	private void checkCourseArrangePosition(Classroom classroom, String period, Integer week) {
		List<ScheduleCycle> scheduleCycles = hasCourseArrange(classroom.getId(), period,
				week);
		if (scheduleCycles.size() != 0) {
			createCheckTeachingTimeError(classroom.getName(), week, period);
		}
	}

	private void createCheckTeachingTimeError(String classroomName, Integer week, String period) {
		throw new InvalidParameterException(
				"班级" + classroomName + "在周" + week
						+ TermCodeUtil.getLessonByPeriod(period) + "已有课程被排");
	}

	@Override
	public Boolean checkTeachingTeacher(Long gradedId, Long teacherId) {
		Teacher teacher = teacherService.getTeacher(teacherId);
		Map<String, Object> map = new HashMap<>();
		map.put("gradedId", gradedId);
		List<GradedSchooltime> gradedSchooltimes = gradedSchooltimeDao.findAll(new GradedSchooltimeSpecification(map));
		for (int j = 0; j < gradedSchooltimes.size(); j++) {
			//该老师在该位置排的必修课是否有课要上
			checkCourseArrangeTeacher(gradedSchooltimes.get(j).getTimeSlot()+"-"+gradedSchooltimes.get(j).getPeriod(), gradedSchooltimes.get(j).getWeek(), teacher);
			//该老师在该位置排的分层课是否有课要上
			checkGradedTeachingTeacher(gradedSchooltimes.get(j).getPeriod(), gradedSchooltimes.get(j).getTimeSlot(), gradedSchooltimes.get(j).getWeek(), teacher);
			//该老师在该位置排的选修课是否有课要上
			//TO DO
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
		if(gradedSchooltime != null){
			createCheckTeachingTeacherError(teacher.getName(), week, timeSlot+"-"+period);
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
		if(scheduleCycle != null){
			createCheckTeachingTeacherError(teacher.getName(), week, period);
		}
		
	}

	private void createCheckTeachingTeacherError(String teacherName, Integer week, String period) {
		throw new InvalidParameterException(
				"老师" + teacherName + "在周" + week
						+ TermCodeUtil.getLessonByPeriod(period) + "已有课程要上");
	}

	@Override
	public Boolean checkTeachingClassroom(Map<String, Object> conditions) {
		// 判断教室在某个星期某节课是否被占用
//		List<String> periods = getCheckPeriod(conditions);
//		for (int j = 0; j < periods.size(); j++) {
//			ScheduleCycle cycle = courseArrangeService.getScheduleCycle(
//					Long.parseLong(conditions.get("buildingRoomId").toString()), periods.get(j),
//					Integer.parseInt(conditions.get("week").toString()));
//			if (cycle != null) {
//				throw new InvalidParameterException("该教室在此时间被占用");
//			}
//		}
		return true;
	}

	private List<String> getCheckPeriod(List<String> morningLesson, List<String> afternoonLesson, List<String> nightLesson) {
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
}
