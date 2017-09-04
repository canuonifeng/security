package com.edu.controller;

import java.util.ArrayList;
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
	private CourseArrangeService sortCourseService;
	
	@RequestMapping(path = "/{classroomId}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classroom', 'add')")
	public Boolean courseArrange(@PathVariable Long classroomId, @RequestBody Map<String, String> conditions) {

		ClassSchedule classSchedule = sortCourseService.getClassSchedule(conditions.get("code"), Long.parseLong(conditions.get("courseId")), classroomId);
		if(classSchedule == null) {
			classSchedule = createClassSchedule(conditions, classroomId);
		}

		createScheduleCycle(conditions, classSchedule);
		
		return true;
	}
	
	@RequestMapping(path = "/classroom/{classroomId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Map<Integer, Map<String, ClassSchedule>> getCourseArrange(@PathVariable Long classroomId, @RequestParam Map<String, String> conditions) {
		Map<Integer, Map<String, ClassSchedule>> list = sortCourseService.getCourseArrange(conditions.get("code"), classroomId);
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
		
		return sortCourseService.createClassSchedule(classSchedule);		
	}
	
	private ScheduleCycle createScheduleCycle(Map<String, String> conditions, ClassSchedule classSchedule) {
		ScheduleCycle scheduleCycle = new ScheduleCycle();
		scheduleCycle.setPeriod(TermCodeUtil.getCoursePeriod(conditions.get("period"), conditions.get("type")));
		scheduleCycle.setWeek(Integer.parseInt(conditions.get("week")));
		scheduleCycle.setClassSchedule(classSchedule);
		return sortCourseService.createScheduleCycle(scheduleCycle);	
	}

}
