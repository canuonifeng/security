package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.FinalGradePart;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseForm;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseVo;
import com.edu.biz.teaching.service.FinalGradeService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/finalgrade")
@Api("课程成绩")
public class FinalGradeController extends BaseController<Course> {
	@Autowired
	private FinalGradeService finalGradeService;
	@Autowired
	private ClassroomService classroomService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('finalgrade', 'get')")
	public List<FinalGradeCourseVo> get(@RequestParam Map<String, Object> conditions) {
		List<Course> courses = finalGradeService.findFinalGradeCourses(conditions);
		List<FinalGradeCourseVo> finalGradeCourseVos = new ArrayList<FinalGradeCourseVo>();
		
		for (Course course : courses) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("finalGradeCourseId", course.getId());
			List<Classroom> classrooms = classroomService.findClassrooms(map);
			FinalGradeCourseVo finalGradeCourseVo = new FinalGradeCourseVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(course, finalGradeCourseVo);
			finalGradeCourseVo.setClassroomCount(classrooms.size());
			map.clear();
			map.put("courseId", course.getId());
			List<FinalGradePartCourse> finalGradePartCourses = finalGradeService.findFinalGradePartCourses(map);
			finalGradeCourseVo.setFinalGradePartCourses(finalGradePartCourses);
			finalGradeCourseVos.add(finalGradeCourseVo);
		}
		return finalGradeCourseVos;
	}

	@RequestMapping(path = "/setpart", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('finalgrade', 'edit')")
	public Boolean edit(@RequestBody FinalGradeCourseForm finalGradeCourseForm) {
		return finalGradeService.setFinalGradePartCourses(finalGradeCourseForm);
	}
	
	@RequestMapping(path = "/part", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('finalgrade', 'get')")
	public List<FinalGradePart> getFinalGradePart(@RequestParam Map<String, Object> conditions) {
		return finalGradeService.findFinalGradeParts(conditions);
	}
}
