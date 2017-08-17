package com.edu.controller;

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

import com.edu.biz.org.entity.OrgJsonViews;
import com.edu.biz.org.entity.Organization;
import com.edu.biz.teachingresources.entity.Course;
import com.edu.biz.teachingresources.service.CourseService;
import com.edu.biz.validgroup.Update;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/course")
@Api("课程")
public class CourseController extends BaseController<Course> {
	@Autowired
	private CourseService courseService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('course', 'add')")
	public Course add(@RequestBody Course course) {
		return courseService.createCourse(course);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('course', 'edit')")
	public Course edit(@PathVariable Long id, @Validated( { Update.class }) @RequestBody Course course) {
		course.setId(id);
		return courseService.updateCourse(course);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('course', 'get')")
	public Course get(@PathVariable Long id) {
		return courseService.getCourse(id);
	}
	
	@RequestMapping(path = "/check_code",method = RequestMethod. GET)
	@ApiOperation(value = "检查课程编号是否重复", notes = "根据课程编号检查是否重复")
	public Boolean checkCode(String code,  Long courseId){
		 return courseService.checkCode(code, courseId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('course', 'get')")
	public Page<Course> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return courseService.searchCourses(conditions, pageable);
	}
}
