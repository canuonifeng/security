package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.service.ClassroomProgramService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/classroomprogram")
@Api("班级教学计划")
public class ClassroomProgramController extends BaseController<Classroom> {

	@Autowired
	private ClassroomProgramService classroomProgramService;

	@RequestMapping(path = "/set/programcourse/{id}/information", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('programCourse', 'edit')")
	public ProgramCourse edit(@PathVariable Long id, @RequestBody Map<String, Object> map) {
		return classroomProgramService.setProgramCourse(id, map);
	}
}
