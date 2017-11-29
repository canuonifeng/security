package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.schoolroll.entity.Classroom;
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
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamAboutFacultyAndGradeAndTestWay> examList(@RequestParam Map<String, Object> conditions) {
		List<ExamAboutFacultyAndGradeAndTestWay> list = new ArrayList<>();
		List<Faculty> facultys = facultyService.findFacultys(conditions);
		for (Faculty faculty:facultys) {
			Map<String, Object> classroomMap = new HashMap<>();
			classroomMap.put("grade", conditions.get("grade"));
			classroomMap.put("facultyId", faculty.getId());
			List<Classroom> classroomList = classroomService.findClassrooms(classroomMap);
			Term currenTerm = TermService.getTermByCurrent(1);
			int count = programService.findWrittenProgramCourses(conditions.get("grade").toString(), faculty.getId(), "written", currenTerm.getCode());
			ExamAboutFacultyAndGradeAndTestWay examList = new ExamAboutFacultyAndGradeAndTestWay();
			examList.setFaculty(faculty);
			examList.setGrade(conditions.get("grade").toString());
			examList.setClassNumber(classroomList.size());
			examList.setExamNumber(count);
			list.add(examList);
		}
		
		return list;
	}
}
