package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentChangeLog;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.schoolroll.service.StudentChangeService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.security.entity.User;
import com.edu.core.exception.NotFoundException;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/studentchange")
@Api("学籍")
public class StudentChangeController extends BaseController<StudentChange> {
	@Autowired
	private StudentChangeService studentChangeService;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('change', 'add')")
	public StudentChange addChange(@RequestBody StudentChange studentChange) {
		Student student = studentService.getStudent(studentChange.getStudent().getId());
		if(student == null) {
			throw new NotFoundException("该学生不存在");
		}
		studentChange.setStudent(student);
		User currenUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		studentChange.setAuditUser(currenUser);
		StudentChange change = studentChangeService.createStudentChange(studentChange);
		
		student.setStatus(StudentStatus.approving);
		studentService.updateStudent(student);
		
		return change;
	}
	
	@RequestMapping(path = "/{id}/audit", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('change', 'edit')")
	public void auditStudentChange(@PathVariable Long id, @RequestBody StudentChangeLog log) {
		StudentChange change = studentChangeService.getStudentChange(id);
		log.setChange(change);
		User currenUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.setOpUser(currenUser);
		studentChangeService.audit(log);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('change', 'delete')")
	public boolean deleteStudentChange(@PathVariable Long id) {
		return studentChangeService.deleteStudentChange(id);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('change', 'get')")
	public StudentChange getStudentChange(@PathVariable Long id) {
		return studentChangeService.getStudentChange(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('change', 'get')")
	public Page<StudentChange> changePager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return studentChangeService.searchStudentChanges(conditions, pageable);	
	}
}
