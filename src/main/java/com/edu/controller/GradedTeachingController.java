package com.edu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/gradedteaching")
@Api("分层教学")
public class GradedTeachingController extends BaseController<GradedTeaching> {
	@Autowired
	private GradedTeachingService gradedTeachingService;
	
	@RequestMapping(path = "/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('GradedTeaching', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<GradedTeaching> findFacultys(@RequestParam Map<String, Object> conditions) {
		List<GradedTeaching> list = gradedTeachingService.findGradedTeachings(conditions);
		
		return list;
	}
}
