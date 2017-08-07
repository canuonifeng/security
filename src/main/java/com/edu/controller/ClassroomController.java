package com.edu.controller;

import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.pojo.ClassroomForm;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.MajorService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/classroom")
@Api("班级")
public class ClassroomController extends BaseController<Classroom> {
	@Autowired
	private ClassroomService classroomService;
	
	@Autowired
	private MajorService majorService;

	@RequestMapping(path = "/batch/add", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classroom', 'add')")
	public Boolean batchAdd(@RequestBody ClassroomForm form) {
		
		int mojorCount = classroomService.countByMajorId(form.getMajorId());
		Major major = majorService.getMajor(form.getMajorId());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        
		for (int i = 0, classroomNum = mojorCount+1; i < form.getNum(); i++) {
			Classroom classroom = new Classroom();
			classroom.setGrade(form.getGrade());
			String name = form.getClassroomSuffix() + classroomNum + form.getClassroomPrefix();
			classroom.setName(name);
			classroom.setMajor(major);

			String code = year + major.getCode() + classroomNum;
			classroom.setCode(code);
			classroomService.createClassroom(classroom);
		}
		return true;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classroom', 'edit')")
	public Classroom edit(@PathVariable Long id, @RequestBody Classroom classroom) {
		classroom.setId(id);
		return classroomService.updateClassroom(classroom);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('classroom', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return classroomService.deleteClassroom(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Classroom get(@PathVariable Long id) {
		Classroom classroom = new Classroom();
		classroom.setId(id);
		return classroomService.getClassroom(classroom.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Page<Classroom> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Classroom> classroom = classroomService.searchClassroom(conditions, pageable);
		return classroom;
	}
}
