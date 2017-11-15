package com.edu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;
import com.edu.biz.teaching.service.GradedSubjectService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/gradesubject")
@Api("分层学科")
public class GradedSubjectController extends BaseController<GradedSubject> {
	@Autowired
	private GradedSubjectService gradedSubjectService;

	@RequestMapping(path = "/batch",  method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedSubject', 'add')")
	public  Boolean create(@RequestBody Map<String, GradedSubject> gradedSubjects) {
		for (String key : gradedSubjects.keySet()) {
			gradedSubjectService.createGradedSubject(gradedSubjects.get(key));
		}
		return true;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('gradedSubject', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return gradedSubjectService.deleteGradedSubject(id);
	}
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedSubject', 'get')")
	public List<GradedSubject> get(@RequestParam Map<String, Object> conditions) {
		return gradedSubjectService.findGradedSubjects(conditions);
	}
	
	@RequestMapping(path = "/result", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedSubject', 'add')")
	public  GradedSubjectResult createResult(@RequestBody GradedSubjectResult gradedSubjectResult) {
		return gradedSubjectService.createResult(gradedSubjectResult);
	}

	@RequestMapping(path = "/{resultId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classroom', 'edit')")
	public GradedSubjectResult edit(@PathVariable Long resultId, @RequestBody GradedSubjectResult gradedSubjectResult) {
		gradedSubjectResult.setId(resultId);
		return gradedSubjectService.updateResult(gradedSubjectResult);
	}
}
