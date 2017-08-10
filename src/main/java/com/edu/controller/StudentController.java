package com.edu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.validgroup.Update;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/student")
@Api("学籍")
public class StudentController extends BaseController<Student> {
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('student', 'add')")
	public Student add(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('student', 'edit')")
	public Student edit(@PathVariable Long id, @Validated( { Update.class }) @RequestBody Student student) {
		student.setId(id);
		return studentService.updateStudent(student);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('student', 'delete')")
	public boolean delete(@PathVariable @ApiParam(name = "id", value = "学生ID", required = true) Long id) {
		return studentService.deleteStudent(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public Student get(@PathVariable Long id) {
		Student student = new Student();
		student.setId(id);
		return studentService.getStudent(student.getId());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public Page<Student> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return studentService.searchStudents(conditions, pageable);
	}
	
	@RequestMapping(path="findStudents", method=RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public List<Student> findStudents(@RequestParam Map<String, Object> conditions) {
		List<Student> list = studentService.findStudents(conditions);
		return list;
	}
}
