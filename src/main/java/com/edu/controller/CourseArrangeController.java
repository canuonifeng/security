package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.common.util.TermCodeUtil;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.pojo.ClassroomVo;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.ProgramCourseVo;
import com.edu.biz.teaching.entity.pojo.ScheduleCycleVo;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.biz.teachingres.service.CourseService;
import com.edu.biz.teachingres.service.TeacherService;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/coursearrange")
@Api("排课")
public class CourseArrangeController extends BaseController<Course> {
	@Autowired
	private TermService termService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	@Autowired
	private BuildingService buildService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ProgramService programService;

	@RequestMapping(path = "/schedule", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classSchedule', 'add')")
	public ScheduleCycleVo courseArrange(@RequestBody Map<String, String> conditions) {

		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(conditions.get("code"),
				Long.parseLong(conditions.get("courseId")), Long.parseLong(conditions.get("classroomId")));
		if (classSchedule == null) {
			classSchedule = createClassSchedule(conditions, Long.parseLong(conditions.get("classroomId")));
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("scheduleId", classSchedule.getId());
			Long arrangeCourseNum = courseArrangeService.countScheduleCyle(map);
			ProgramCourse programCourse = programService.getProgramCourse(Long.parseLong(conditions.get("courseId")),
					conditions.get("code"));
			Integer remainderCourseNum = programCourse.getWeekPeriod() - Integer.parseInt(arrangeCourseNum.toString());
			if (remainderCourseNum < 0) {
				throw new InvalidParameterException("课程" + programCourse.getCourse().getName() + "排课超出周课时");
			}
		}

		ScheduleCycleVo scheduleCycleVo = createScheduleCycle(conditions, classSchedule);

		return scheduleCycleVo;
	}
	
	@RequestMapping(path = "schedule/{scheduleId}/mergeclassroom", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classSchedule', 'edit')")
	public Boolean mergeClassroom(@PathVariable Long scheduleId, @RequestBody Map<String, String> classroomIds) {
		List<Long> mergeClassroomIds = new ArrayList<>();
		for (String key : classroomIds.keySet()) {
			mergeClassroomIds.add(Long.parseLong(classroomIds.get(key)));
		}
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("classroomIds", mergeClassroomIds);
		List<Classroom> classrooms = classroomService.findClassrooms(conditions);
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if(classSchedule == null) {
			throw new NotFoundException("该排课不存在");
		}
		classSchedule.setClassrooms(classrooms);
		courseArrangeService.updateClassSchedule(classSchedule);
		return true;
	}
	
	
	@RequestMapping(path = "schedule/{scheduleId}/cycle/{cycleId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classSchedule', 'edit')")
	public ScheduleCycle updateCourseArrange(@PathVariable Long scheduleId, @PathVariable Long cycleId,
			@RequestBody Map<String, String> conditions) {
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);
		if (scheduleCycle == null) {
			throw new NotFoundException("该排课周期不存在");
		}
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if (!classSchedule.getCourse().getId().equals(Long.parseLong(conditions.get("courseId")))) {
			throw new InvalidParameterException("拖动课程不一致");
		}
		courseArrangeService.deleteScheduleCycle(scheduleCycle.getId());
		return createScheduleCycle(conditions, classSchedule);
	}

	@RequestMapping(path = "schedule/{scheduleId}/cycle/{cycleId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('classSchedule', 'delete')")
	public Boolean removeCourseArrange(@PathVariable Long scheduleId, @PathVariable Long cycleId) {
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);
		if (scheduleCycle == null) {
			throw new NotFoundException("该排课周期不存在");
		}
		courseArrangeService.deleteScheduleCycle(scheduleCycle.getId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduleId", scheduleCycle.getClassSchedule().getId());
		List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(map);
		if (scheduleCycles.size() == 0) {
			courseArrangeService.deleteClassSchedule(scheduleCycle.getClassSchedule().getId());
		}
		return true;
	}

	@RequestMapping(path = "/allcourses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<ProgramCourseVo> termCourses(@RequestParam Map<String, Object> conditions) {
		List<ProgramCourse> programCourses = programService.searchAllProgramCourse(conditions);
		List<ProgramCourseVo> programCourseVos = new ArrayList<>();
		for (ProgramCourse programCourse : programCourses) {
			ProgramCourseVo programCourseVo = buildProgramCourseVo(programCourse, conditions);
			if (programCourseVo.getRemainderCourseNum().equals(0)) {
				continue;
			}
			programCourseVos.add(programCourseVo);
		}
		return programCourseVos;
	}

	@RequestMapping(path = "/schedule", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classSchedule', 'get')")
	@JsonView(TeachingresJsonViews.CascadeCourse.class)
	public Map<Integer, Map<String, ScheduleCycleVo>> getCourseArrange(@RequestParam Map<String, String> conditions) {
		Map<Integer, Map<String, ScheduleCycleVo>> list = courseArrangeService.getCourseArrange(conditions.get("code"),
				Long.parseLong(conditions.get("classroomId")));
		return list;
	}

	@RequestMapping(path = "/schedule/{scheduleId}/cycle/{cycleId}/setteacher", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classSchedule', 'post')")
	public ScheduleCycle setCourseTeacher(@PathVariable Long scheduleId, @PathVariable Long cycleId,
			@RequestBody Map<String, Object> conditions) {
		List<ScheduleTeacher> scheduleTeachers = createScheduleTeachers(scheduleId, conditions);
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);

		ScheduleCycleVo scheduleCycleVo = new ScheduleCycleVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(scheduleCycle, scheduleCycleVo);
		scheduleCycleVo.setScheduleTeacher(scheduleTeachers);

		return scheduleCycleVo;
	}
	
	@RequestMapping(path = "schedule/{scheduleId}/mergeclassroom", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<ClassroomVo> getMergeClassroom(@PathVariable Long scheduleId, @RequestParam Map<String, Object> conditions) {
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if (classSchedule == null) {
			throw new NotFoundException("该排课不存在");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("mergeCourseId", classSchedule.getCourse().getId());
		map.put("termCode", conditions.get("termCode"));
		List<Classroom> classrooms = classroomService.findClassrooms(map);
		
		//判断班级集合在位置集合有没有冲突
		map.clear();
		map.put("scheduleId", classSchedule.getId());
		List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(map);
		return classroomCategory(classrooms, scheduleCycles);
	}
	
	@RequestMapping(path = "/schedule/{scheduleId}/cycle/{cycleId}/setroom", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classSchedule', 'put')")
	public ScheduleCycle setCourseRoom(@PathVariable Long scheduleId, @PathVariable Long cycleId,
			@RequestBody Map<String, Object> conditions) {
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if (classSchedule == null) {
			throw new NotFoundException("该排课不存在");
		}

		return updateScheduleCycle(cycleId, conditions);
	}

	private ClassSchedule createClassSchedule(Map<String, String> conditions, Long classroomId) {
		Term term = termService.getTermByCode(conditions.get("code"));
		Course course = courseService.getCourse(Long.parseLong(conditions.get("courseId")));
		if (term == null) {
			throw new NotFoundException("该学期不存在");
		}
		ClassSchedule classSchedule = new ClassSchedule();
		Classroom classroom = classroomService.getClassroom(classroomId);
		List<Classroom> classrooms = new ArrayList<Classroom>();
		classrooms.add(classroom);
		classSchedule.setTerm(term.getCode());
		classSchedule.setCourse(course);
		classSchedule.setClassrooms(classrooms);

		return courseArrangeService.createClassSchedule(classSchedule);
	}

	private ScheduleCycleVo createScheduleCycle(Map<String, String> conditions, ClassSchedule classSchedule) {
		String period = TermCodeUtil.getCoursePeriod(conditions.get("period"), conditions.get("type"));
		Integer week = Integer.parseInt(conditions.get("week"));
		List<ScheduleCycle> scheduleCycles = classroomHasOtherCourseArrange(Long.parseLong(conditions.get("classroomId")), period, week);
		if (scheduleCycles.size() != 0) {
			throw new InvalidParameterException("该位置已有课程被排");
		}
		ScheduleTeacher scheduleTeacher = courseArrangeService.getMasterScheduleTeacher(classSchedule.getId());
		// 判断老师在某个星期某节课。是否有其他课程需要上
		if(scheduleTeacher != null) {
			teacherHasOtherCourseArrange(scheduleTeacher.getTeacher().getId(), period, week);
		}

		ScheduleCycle scheduleCycle = new ScheduleCycle();
		scheduleCycle.setPeriod(period);
		scheduleCycle.setWeek(week);
		scheduleCycle.setClassSchedule(classSchedule);
		scheduleCycle = courseArrangeService.createScheduleCycle(scheduleCycle);
		ScheduleCycleVo scheduleCycleVo = new ScheduleCycleVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(scheduleCycle, scheduleCycleVo);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("scheduleId", scheduleCycle.getClassSchedule().getId());
		List<ScheduleTeacher> scheduleTeachers = courseArrangeService.findScheduleTeachers(condition);
		scheduleCycleVo.setScheduleTeacher(scheduleTeachers);
		return scheduleCycleVo;
	}

	private List<ScheduleTeacher> createScheduleTeachers(Long scheduleId, Map<String, Object> conditions) {
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if (classSchedule == null) {
			throw new NotFoundException("该排课不存在");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("scheduleId", scheduleId);
		List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(map);
		//判断所有该课程位置上主带老师是否有课要上
		for (ScheduleCycle scheduleCycle : scheduleCycles) {
			teacherHasOtherCourseArrange((Long.parseLong(conditions.get("0").toString())), scheduleCycle.getPeriod(), scheduleCycle.getWeek());
		}
		courseArrangeService.deleteScheduleTeacherByScheduleId(classSchedule.getId());
		List<ScheduleTeacher> scheduleTeachers = new ArrayList<>();	
		for (String key : conditions.keySet()) {
			Teacher teacher = teacherService.getTeacher(Long.parseLong(conditions.get(key).toString()));
			if (teacher == null) {
				throw new NotFoundException("该老师不存在");
			}
			ScheduleTeacher scheduleTeacher = new ScheduleTeacher();
			scheduleTeacher.setClassSchedule(classSchedule);
			scheduleTeacher.setTeacher(teacher);
			if (key.equals("0")) {
				scheduleTeacher.setMaster(1);
			} else {
				scheduleTeacher.setMaster(0);
			}

			courseArrangeService.createScheduleTeacher(scheduleTeacher);
			scheduleTeachers.add(scheduleTeacher);
		}
		return scheduleTeachers;
	}

	private ScheduleCycle updateScheduleCycle(Long cycleId, Map<String, Object> conditions) {
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);
		if (scheduleCycle == null) {
			throw new NotFoundException("该排课周期不存在");
		}
		BuildingRoom buildingRoom = buildService.getBuildingRoom(Long.parseLong(conditions.get("roomId").toString()));
		if (buildingRoom == null) {
			throw new NotFoundException("教室不存在");
		}
		// 判断教室在某个星期某节课是否被占用
		ScheduleCycle cycle = courseArrangeService.getScheduleCycle(buildingRoom.getId(), scheduleCycle.getPeriod(),
				scheduleCycle.getWeek());
		if (cycle != null) {
			throw new InvalidParameterException("该教室在此时间被占用");
		}
		scheduleCycle.setBuildingRoom(buildingRoom);
		return scheduleCycle;
	}

	private ProgramCourseVo buildProgramCourseVo(ProgramCourse programCourse, Map<String, Object> conditions) {
		ProgramCourseVo programCourseVo = new ProgramCourseVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(programCourse, programCourseVo);
		programCourseVo.setRemainderCourseNum(programCourse.getWeekPeriod());
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(programCourse.getTermCode(),
				programCourse.getCourse().getId(), Long.parseLong(conditions.get("classroomId").toString()));
		if (classSchedule != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("scheduleId", classSchedule.getId());
			Long arrangeCourseNum = courseArrangeService.countScheduleCyle(map);
			Integer remainderCourseNum = programCourse.getWeekPeriod() - Integer.parseInt(arrangeCourseNum.toString());
			if (remainderCourseNum < 0) {
				throw new InvalidParameterException("课程" + programCourse.getCourse().getName() + "排课超出周课时");
			}
			programCourseVo.setRemainderCourseNum(remainderCourseNum);
		}
		return programCourseVo;
	}

	private Boolean teacherHasOtherCourseArrange(Long teacherId, String period, Integer week) {
		Teacher teacher = teacherService.getTeacher(teacherId);
		if (teacher == null) {
			throw new NotFoundException("老师不存在");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("period", period);
		map.put("week", week);
		map.put("teacherId", teacher.getId());
		map.put("master", 1);
		List<ScheduleCycle> scheduleCycle = courseArrangeService.findScheduleCycles(map);
		if (scheduleCycle.size() != 0) {
			throw new InvalidParameterException("老师在该时间段还有其他课程需要上");
		}
		return true;
	}

	private List<ScheduleCycle> classroomHasOtherCourseArrange(Long classroomId, String period, Integer week) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("period", period);
		map.put("week", week);
		map.put("classroomId", classroomId);
		// 判断某个星期的某节课，是否有课程
		return courseArrangeService.findScheduleCycles(map);
	}
	
	private List<ClassroomVo> classroomCategory(List<Classroom> classrooms, List<ScheduleCycle> scheduleCycles){
		List<ClassroomVo> classroomVos = new ArrayList<>();
		for (int i = 0; i < classrooms.size(); i++) {
			ClassroomVo classroomVo = new ClassroomVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(classrooms.get(i), classroomVo);
			for (int j = 0; j < scheduleCycles.size(); j++) {
				List<ScheduleCycle> cycles = classroomHasOtherCourseArrange(classroomVo.getId(), scheduleCycles.get(j).getPeriod(), scheduleCycles.get(j).getWeek());
				if(cycles.size() != 0) {
					classroomVo.setHasOtherCourse(1);
					break;
				}
			}
			classroomVos.add(classroomVo);
		}
		return classroomVos;
	}
}
