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

import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.exam.service.ExamArrangeService;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.pojo.CourseVo;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/examarrange")
@Api("排考")
public class ExamArrangeController extends BaseController<Faculty> {
	@Autowired
	private ExamArrangeService examArrangeService;
	@Autowired
	private TermService termService;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamAboutFacultyAndGradeAndTestWay> examList(@RequestParam Map<String, Object> conditions) {
		return examArrangeService.getExamList(conditions);
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('examArrange', 'add')")
	public void modify(@RequestBody ExamArrange examArrange) {
		Term term = termService.getTermByCurrent(1);
		examArrange.setTermCode(term.getCode());
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", examArrange.getCourse().getId());
		map.put("facultyId", examArrange.getFaculty().getId());
		map.put("grade", examArrange.getGrade());
		map.put("termCode", examArrange.getTermCode());
		ExamArrange exam = examArrangeService.getExamArrange(map);
		if(exam != null) {
			examArrange.setId(exam.getId());
			examArrangeService.updateExamArrange(examArrange);
		}
		examArrangeService.createExamArrange(examArrange);
	}
	
	@RequestMapping(path="/classroomexam", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<ExamArrange> getClassroomExamArrange(@RequestParam Map<String, Object> conditions) {
		return examArrangeService.findClassroomExamArranges(conditions);
	}

	@RequestMapping(path = "/courses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('examArrange', 'get')")
	public List<CourseVo> findCurrentTermExamArrangeCourses(@RequestParam Map<String, Object> conditions) {
		Term term = termService.getTermByCurrent(1);
		conditions.put("termCode", term.getCode());
		List<Course> examCourses = examArrangeService.findExamArrangeCourses(conditions);
		if (conditions.containsKey("facultyId") && conditions.containsKey("grade")) {
			return dealExamCourses(examCourses, conditions);
		}
		List<CourseVo> courseVos = new ArrayList<CourseVo>();
		for (Course course : examCourses) {
			CourseVo courseVo = new CourseVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(course, courseVo);
			courseVos.add(courseVo);
		}
		return courseVos;
	}

	private List<CourseVo> dealExamCourses(List<Course> examCourses, Map<String, Object> conditions) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("facultyId", conditions.get("facultyId"));
		map.put("grade", conditions.get("grade"));
		List<CourseVo> courseVos = new ArrayList<CourseVo>();
		for (Course course : examCourses) {
			CourseVo courseVo = new CourseVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(course, courseVo);
			map.put("courseId", course.getId());
			ExamArrange examArrange = examArrangeService.getExamArrange(map);
			if (examArrange != null) {
				courseVo.setExamStartTime(examArrange.getExamStartTime());
				courseVo.setExamEndTime(examArrange.getExamEndTime());
			}
			courseVos.add(courseVo);
		}
		return courseVos;
	}
}
