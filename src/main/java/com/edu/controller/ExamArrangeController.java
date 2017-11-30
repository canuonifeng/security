package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/examarrange")
@Api("排考")
public class ExamArrangeController extends BaseController<Faculty> {
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private ProgramService programService;
	@Autowired
	private TermService TermService;
	@Autowired
	private ExamArrangeService examArrangeService;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamAboutFacultyAndGradeAndTestWay> examList(@RequestParam Map<String, Object> conditions) {
		List<ExamAboutFacultyAndGradeAndTestWay> list = new ArrayList<>();
		List<Faculty> facultys = facultyService.findFacultys(conditions);
		for (Faculty faculty:facultys) {
			Map<String, Object> classroomMap = new HashMap<>();
			classroomMap.put("grade", conditions.get("grade"));
			classroomMap.put("facultyId", faculty.getId());
			Long classroomCount = classroomService.countClassroom(classroomMap);
			Term currenTerm = TermService.getTermByCurrent(1);
			int courseCount = programService.countWrittenProgramCourses(conditions.get("grade").toString(), faculty.getId(), "written", currenTerm.getCode());
			ExamAboutFacultyAndGradeAndTestWay examList = new ExamAboutFacultyAndGradeAndTestWay();
			examList.setFaculty(faculty);
			examList.setGrade(conditions.get("grade").toString());
			examList.setClassNumber(Integer.valueOf(classroomCount.toString()));
			examList.setExamNumber(courseCount);
			list.add(examList);
		}
		
		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('examArrange', 'add')")
	public void add(@RequestBody List<ExamArrange> examArranges) {
		for (ExamArrange examArrang:examArranges) {
			examArrangeService.createExamArrange(examArrang);
		}
	}
}
