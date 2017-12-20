package com.edu.controller;

import java.util.ArrayList;
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
import com.edu.biz.teaching.entity.CurrentStep;
import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.entity.TeachingJsonViews;
import com.edu.biz.teaching.entity.pojo.SelectCourseTimeCheckForm;
import com.edu.biz.teaching.entity.pojo.SelectCourseVo;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.edu.biz.validgroup.Update;
import com.edu.core.util.BeanUtils;
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
	@JsonView({ TeachingJsonViews.CascadeSelectCourseClass.class })
	public List<SelectCourseVo> findSelectCourse(@RequestParam Map<String, Object> conditions) {
		List<SelectCourseVo> list = new ArrayList<>();
		List<SelectCourse> selectCourses = selectCourseService.findSelectCourses(conditions);
		for (SelectCourse selectCourse : selectCourses) {
			Map<String, Object> map = new HashMap<>();
			map.put("selectCourseId", selectCourse.getId());
			List<SelectCourseSchooltime> times = selectCourseService.findTimes(map);
			SelectCourseVo selectCourseVo = new SelectCourseVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(selectCourse, selectCourseVo);
			selectCourseVo.setSelectCourseSchooltimes(times);
			list.add(selectCourseVo);
		}
		return list;
	}

	@RequestMapping(path = "/check/selectcoursetime", method = RequestMethod.POST)
	public Boolean checkTeachingTime(@RequestBody SelectCourseTimeCheckForm selectCourseTimeCheckForm) {
		return selectCourseService.checkSelectCourseTime(selectCourseTimeCheckForm);
	}
	
	@RequestMapping(path= "/class",method = RequestMethod.POST)
	@PreAuthorize("hasPermission('selectCourseClass', 'add')")
	public void saveCourse(@RequestBody List<SelectCourseClassAndClassSchooltime> list) {
		selectCourseService.saveClass(list);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('selectCourse', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public SelectCourse get(@PathVariable Long id) {
		return selectCourseService.getSelectCourse(id);
	}
	
	@RequestMapping(path = "/{id}/times", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('selectCourseSchooltime', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<SelectCourseSchooltime> findTimes(@PathVariable Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("selectCourseId", id);
		return selectCourseService.findTimes(conditions);
	}
	
	@RequestMapping(path = "/{id}/class", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('selectCourseClass', 'get')")
	public List<SelectCourseClassAndClassSchooltime> findClass(@PathVariable Long id) {
		List<SelectCourseClassAndClassSchooltime> selectCourseClassAndClassSchooltimes = selectCourseService.findClass(id);
		for (SelectCourseClassAndClassSchooltime selectCourseClassAndClassSchooltime : selectCourseClassAndClassSchooltimes) {
			selectCourseClassAndClassSchooltime.getSelectCourseClass().getTeacher().setCourses(null);
			selectCourseClassAndClassSchooltime.getSelectCourseClass().getSelectCourse().setSelectCourseClasses(null);
		}
		return selectCourseClassAndClassSchooltimes;
	}
	
	@RequestMapping(path = "/{id}/times", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedTime', 'edit')")
	public void editTimes(@PathVariable Long id, @Validated({ Update.class }) @RequestBody List<SelectCourseSchooltime> list) {
		selectCourseService.updateSchooltimes(id, list);
	}

	@RequestMapping(path = "/{id}/selectcoursebuildingroom", method = RequestMethod.GET)
	public Map<String, List<BuildingRoom>> findWeekBuildingRoom(Long id) {
		return selectCourseService.findWeekBuildingRoom(id);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('selectCourse', 'edit')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public SelectCourse edit(@PathVariable Long id, @Validated({ Update.class }) @RequestBody SelectCourse selectCourse) {
		selectCourse.setId(id);
		return selectCourseService.updateSelectCourse(selectCourse);
	}
	
	@RequestMapping(path = "/course/{courseId}/classrooms", method = RequestMethod.GET)
	public List<Classroom> findGradedTeachingClassrooms(@PathVariable Long courseId, @RequestParam Map<String, Object> conditions) {
		conditions.put("courseId", courseId);
		return selectCourseService.findSelectCourseClassrooms(conditions);
	}

	@RequestMapping(path = "/{id}/course/{courseId}/classrooms", method = RequestMethod.GET)
	public List<Classroom> findUpdateGradedTeachingClassrooms(@PathVariable Long courseId, @PathVariable Long id, @RequestParam Map<String, Object> conditions) {
		conditions.put("courseId", courseId);
		conditions.put("selectCourseId", id);
		return selectCourseService.findSelectCourseClassrooms(conditions);
	}
	
	@RequestMapping(path = "/{id}/class", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('selectCourseClass', 'edit')")
	public void editClass(@PathVariable Long id, @Validated({ Update.class }) @RequestBody List<SelectCourseClassAndClassSchooltime> list) {
		selectCourseService.updateSelectCourseClass(id, list);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('selectCourse', 'delete')")
	public boolean deleteSelectCourse(@PathVariable Long id) {
		return selectCourseService.deleteSelectCourse(id);
	}
	
	@RequestMapping(path = "/{id}/currentpage", method = RequestMethod.GET)
	public CurrentStep getCurrentStep(@PathVariable Long id) {
		String page = selectCourseService.getCurrentStep(id);
		CurrentStep currentStep = new CurrentStep();
		currentStep.setCurrentPage(page);
		return currentStep;
	}
}
