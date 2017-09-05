package com.edu.controller;

import java.util.List;
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

import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.entity.TeacherStatus;
import com.edu.biz.teachingres.service.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/teacher")
@Api("师资")
public class TeacherController extends BaseController<Teacher> {
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('teacher', 'add')")
	public Teacher add(@RequestBody Teacher teacher) {
		return teacherService.createTeacher(teacher);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('teacher', 'edit')")
	public Teacher edit(@PathVariable Long id, @RequestBody Teacher teacher) {
		teacher.setId(id);
		return teacherService.updateTeacher(teacher);
	}
	
	@RequestMapping(path = "/{id}/status", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('teacher', 'edit')")
	@ApiOperation(value = "修改教师状态")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public Teacher changeTeacherStatus(@PathVariable @ApiParam(name = "id", value = "院系ID", required = true) Long id,
			@RequestBody @ApiParam(name = "status", value = "enable(在职), vacation(休假),retired(退休),departure(离职),learnOut(外出学习)", required = true) Map<String, String> params) {
		TeacherStatus status = TeacherStatus.valueOf(params.get("status"));
		return teacherService.changeTeacherStatus(id, status);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('teacher', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return teacherService.deleteTeacher(id);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('teacher', 'get')")
	public Teacher get(@PathVariable Long id) {
		Teacher teacher = new Teacher();
		teacher.setId(id);
		return teacherService.getTeacher(teacher.getId());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('teacher', 'get')")
	public Page<Teacher> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return teacherService.searchTeachers(conditions, pageable);
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('teacher', 'get')")
	public List<Teacher> findTeachers(@RequestParam Map<String, Object> conditions) {
		return teacherService.findTeachers(conditions);
	}
}
