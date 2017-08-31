package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.common.util.StringUtil;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleClassroom;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.SortCourseService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.service.CourseService;
import com.edu.core.exception.NotFoundException;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/sortcourse")
@Api("排课")
public class SortCourseController extends BaseController<Course> {
	@Autowired
	private TermService termService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private SortCourseService sortCourseService;
	@RequestMapping(path = "/{classroomId}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classroom', 'add')")
	public Boolean sortCourse(@PathVariable Long classroomId, @RequestBody Map<String, String> conditions) {
//		String grade = conditions.get("grade");
//		Integer termNum = Integer.parseInt(conditions.get("termNum"));
//		String code = StringUtil.getTermCode(grade, termNum);
		
//		ClassSchedule schedule = sortCourseService.getClassDcheduleByClassroomIdAndCourseId(Long classroomId, );
		
		ClassSchedule classSchedule = createClassSchedule(conditions);

		createScheduleCycle(conditions, classSchedule);
		
		createScheduleClassroom(conditions, classSchedule, classroomId);
		
		return true;
	}
	
	private ClassSchedule createClassSchedule(Map<String, String> conditions) {
		Term term = termService.getTermByCode(conditions.get("code"));
		Course course = courseService.getCourse(Long.parseLong(conditions.get("courseId")));
		if(term == null) {
			throw new NotFoundException("该学期不存在");
		}
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setTerm(term.getCode());
		classSchedule.setCourse(course);
		
		return sortCourseService.createClassSchedule(classSchedule);		
	}
	
	private ScheduleCycle createScheduleCycle(Map<String, String> conditions, ClassSchedule classSchedule) {
		ScheduleCycle scheduleCycle = new ScheduleCycle();
		scheduleCycle.setPeriod(StringUtil.getCoursePeriod(conditions.get("period"), conditions.get("type")));
		scheduleCycle.setWeek(Integer.parseInt(conditions.get("week")));
		scheduleCycle.setClassSchedule(classSchedule);
		return sortCourseService.createScheduleCycle(scheduleCycle);	
	}
	
	private ScheduleClassroom createScheduleClassroom(Map<String, String> conditions, ClassSchedule classSchedule, Long classroomId) {
		Classroom classroom = classroomService.getClassroom(classroomId);
		if(classroom == null) {
			throw new NotFoundException("该班级不存在");
		}
		ScheduleClassroom scheduleCalssroom = new ScheduleClassroom();
		scheduleCalssroom.setClassroom(classroom);
		scheduleCalssroom.setClassSchedule(classSchedule);
		return sortCourseService.createScheduleClassroom(scheduleCalssroom);
	}
}
