package com.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.edu.biz.validgroup.Update;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/gradedteaching")
@Api("分层教学")
public class GradedTeachingController extends BaseController<GradedTeaching> {
	@Autowired
	private GradedTeachingService gradedTeachingService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedTeaching', 'add')")
	public GradedTeaching add(@RequestBody GradedTeaching graded) {
		return gradedTeachingService.createGraded(graded);
	}

	@RequestMapping(path = "/schooltime", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedSchooltime', 'add')")
	public void addTime(@RequestBody List<GradedSchooltime> list) {
		gradedTeachingService.createSchooltimes(list);
	}

	@RequestMapping(path = "/rank", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedRank', 'add')")
	public void addRank(@RequestBody List<GradedRank> list) {
		gradedTeachingService.createRank(list);
	}
	
	@RequestMapping(path= "/course",method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedRank', 'add')")
	public void addCourse(@RequestBody List<GradedCourseAndCourseTime> list) {
		gradedTeachingService.createCourse(list);
	}
	
	@RequestMapping(path = "/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedTeaching', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<GradedTeaching> findGradedTeaching(@RequestParam Map<String, Object> conditions) {
		List<GradedTeaching> list = gradedTeachingService.findGradedTeachings(conditions);

		return list;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedTeaching', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public GradedTeaching get(@PathVariable Long id) {
		return gradedTeachingService.getGradedTeaching(id);
	}
	
	@RequestMapping(path = "/{id}/ranks", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedRank', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<GradedRank> findRanks(@PathVariable Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("gradedId", id);
		return gradedTeachingService.findRanks(conditions);
	}
	
	@RequestMapping(path = "/{id}/times", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedSchooltime', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<GradedSchooltime> findTimes(@PathVariable Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("gradedId", id);
		return gradedTeachingService.findTimes(conditions);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedTeaching', 'edit')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public GradedTeaching edit(@PathVariable Long id, @Validated({ Update.class }) @RequestBody GradedTeaching graded) {
		graded.setId(id);
		return gradedTeachingService.updateGradedTeaching(graded);
	}

	@RequestMapping(path = "/course/{courseId}/classrooms", method = RequestMethod.GET)
	public List<Classroom> findGradedTeachingClassrooms(@PathVariable Long courseId, @RequestParam Map<String, Object> conditions) {
		conditions.put("courseId", courseId);
		return gradedTeachingService.findGradedTeachingClassrooms(conditions);
	}

	@RequestMapping(path = "/check/teachingtime", method = RequestMethod.POST)
	public Boolean checkTeachingTime(@RequestBody GradedTimeCheckForm gradedTimeCheckForm) {
		return gradedTeachingService.checkTeachingTime(gradedTimeCheckForm);
	}

	@RequestMapping(path = "/check/teachingclassroom", method = RequestMethod.GET)
	public Boolean checkTeachingClassroom(@RequestParam Map<String, Object> conditions) {

		return gradedTeachingService.checkTeachingClassroom(conditions);
	}
}
