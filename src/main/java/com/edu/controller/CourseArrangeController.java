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
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.service.CourseService;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;

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
	
	@RequestMapping(path = "/schedule", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classSchedule', 'add')")
	public ScheduleCycle courseArrange( @RequestBody Map<String, String> conditions) {

		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(conditions.get("code"), Long.parseLong(conditions.get("courseId")), Long.parseLong(conditions.get("classroomId")));
		if(classSchedule == null) {
			classSchedule = createClassSchedule(conditions, Long.parseLong(conditions.get("classroomId")));
		}

		ScheduleCycle scheduleCycle =  createScheduleCycle(conditions, classSchedule);
		
		return scheduleCycle;
	}
	
	@RequestMapping(path = "schedule/{scheduleId}/cycle/{cycleId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classSchedule', 'update')")
	public ScheduleCycle updateCourseArrange(@PathVariable Long scheduleId, @PathVariable Long cycleId, @RequestBody Map<String, String> conditions) {
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);
		if(scheduleCycle == null) {
			throw new NotFoundException("该排课周期不存在");
		}
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(scheduleId);
		if(!classSchedule.getCourse().getId().equals(Long.parseLong(conditions.get("courseId")))) {
			throw new InvalidParameterException("拖动课程不一致");
		}
		courseArrangeService.deleteScheduleCycle(scheduleCycle.getId());
		return  createScheduleCycle(conditions, classSchedule);
	}
	
	@RequestMapping(path = "schedule/{scheduleId}/cycle/{cycleId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('classSchedule', 'delete')")
	public Boolean removeCourseArrange(@PathVariable Long scheduleId, @PathVariable Long cycleId) {
		ScheduleCycle scheduleCycle = courseArrangeService.getScheduleCycle(cycleId);
		if(scheduleCycle == null) {
			throw new NotFoundException("该排课周期不存在");
		}
		courseArrangeService.deleteScheduleCycle(scheduleCycle.getId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduleId", scheduleCycle.getClassSchedule().getId());
		List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(map);
		if(scheduleCycles.size() == 0) {
			courseArrangeService.deleteClassSchedule(scheduleCycle.getClassSchedule().getId());
		}
		return true;
	}
	
	@RequestMapping(path = "/schedule", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classSchedule', 'get')")
	public Map<Integer, Map<String, ScheduleCycle>> getCourseArrange( @RequestParam Map<String, String> conditions) {
		Map<Integer, Map<String, ScheduleCycle>> list = courseArrangeService.getCourseArrange(conditions.get("code"), Long.parseLong(conditions.get("classroomId")));
		return list;
	}
	
	private ClassSchedule createClassSchedule(Map<String, String> conditions, Long classroomId) {
		Term term = termService.getTermByCode(conditions.get("code"));
		Course course = courseService.getCourse(Long.parseLong(conditions.get("courseId")));
		if(term == null) {
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
	
	private ScheduleCycle createScheduleCycle(Map<String, String> conditions, ClassSchedule classSchedule) {
		ScheduleCycle scheduleCycle = new ScheduleCycle();
		scheduleCycle.setPeriod(TermCodeUtil.getCoursePeriod(conditions.get("period"), conditions.get("type")));
		scheduleCycle.setWeek(Integer.parseInt(conditions.get("week")));
		scheduleCycle.setClassSchedule(classSchedule);
		return courseArrangeService.createScheduleCycle(scheduleCycle);
	}

}
