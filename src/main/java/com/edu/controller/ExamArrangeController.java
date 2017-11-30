package com.edu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.exam.service.ExamArrangeService;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teachingres.entity.Course;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/examarrange")
@Api("排考")
public class ExamArrangeController extends BaseController<Faculty> {
	@Autowired
	private ExamArrangeService examArrangeService;
	@Autowired
	private ClassroomService classroomService;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamAboutFacultyAndGradeAndTestWay> examList(@RequestParam Map<String, Object> conditions) {
		return examArrangeService.getExamList(conditions);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('examArrange', 'add')")
	public void add(@RequestBody List<ExamArrange> examArranges) {
		for (ExamArrange examArrang:examArranges) {
			examArrangeService.createExamArrange(examArrang);
		}
	}
	
	@RequestMapping(path="/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamArrange> getClassroomExamArrange(@RequestParam Map<String, Object> conditions) {
		Classroom classroom = classroomService.getClassroom(Long.valueOf(conditions.get("classroomId").toString()));
		conditions.put("programId", classroom.getProgram().getId());
		conditions.put("facultyId", classroom.getMajor().getFaculty().getId());
		conditions.put("grade", classroom.getGrade());
		return examArrangeService.findExamArranges(conditions);
	}
	
	@RequestMapping(path = "/courses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<Course> get(@RequestParam Map<String, Object> conditions) {
		return examArrangeService.findExamArrangeCourses(conditions);
	}
}
