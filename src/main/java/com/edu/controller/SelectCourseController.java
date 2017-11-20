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

import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassCourse;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/selectcourse")
@Api("选课")
public class SelectCourseController extends BaseController<SelectCourse> {
	@Autowired
	private SelectCourseService selectCourseService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('selectCourse', 'add')")
	public SelectCourse add(@RequestBody SelectCourse selectCoure) {
		return selectCourseService.createSelectCourse(selectCoure);
	}
	
	@RequestMapping(path = "/schooltime", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('selectCourseSchooltime', 'add')")
	public void addTime(@RequestBody List<SelectCourseSchooltime> list) {
		selectCourseService.createSchooltimes(list);
	}
	
	@RequestMapping(path = "/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('selectCourse', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<SelectCourse> findGradedTeaching(@RequestParam Map<String, Object> conditions) {
		List<SelectCourse> list = selectCourseService.findSelectCourses(conditions);

		return list;
	}
	
	@RequestMapping(path= "/class",method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedCourse', 'add')")
	public void saveCourse(@RequestBody List<SelectCourseClassAndClassCourse> list) {
		selectCourseService.saveClass(list);
	}
}
