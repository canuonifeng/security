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
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.entity.FinalGradePart;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.FinalGradePartStudent;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseForm;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseVo;
import com.edu.biz.teaching.entity.pojo.FinalGradePartStudentScoreForm;
import com.edu.biz.teaching.entity.pojo.FinalGradeStudentVo;
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
	@Autowired
	private StudentService studentService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('finalgrade', 'get')")
	public List<FinalGradeCourseVo> get(@RequestParam Map<String, Object> conditions) {
		if(!conditions.containsKey("facultyId") || !conditions.containsKey("facultyId")){
			return new ArrayList<>();
		}
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
	public List<FinalGradePart> findFinalGradePart(@RequestParam Map<String, Object> conditions) {
		return finalGradeService.findFinalGradeParts(conditions);
	}

	@RequestMapping(path = "/partcourse", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('finalgrade', 'get')")
	public List<FinalGradePartCourse> findFinalGradePartCourse(@RequestParam Map<String, Object> conditions) {
		return finalGradeService.findFinalGradePartCourses(conditions);
	}

	@RequestMapping(path = "/student", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('finalgrade', 'get')")
	public List<FinalGradeStudentVo> findFinalGradePartS(@RequestParam Map<String, Object> conditions) {
		Map<String, Object> query = new HashMap<>();
		query.put("classroomId", conditions.get("classroomId"));
		List<Student> students = finalGradeService.findFinalGradeStudents(query);
		List<FinalGradeStudentVo> finalGradeStudentVos = new ArrayList<FinalGradeStudentVo>();
		for (Student student : students) {
			FinalGradeStudentVo finalGradeStudentVo = new FinalGradeStudentVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(student, finalGradeStudentVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("facultyId", conditions.get("facultyId"));
			map.put("termCode", conditions.get("termCode"));
			map.put("courseId", conditions.get("courseId"));
			List<FinalGradePartCourse> finalGradePartCourses = finalGradeService.findFinalGradePartCourses(map);
			finalGradeStudentVo.setFinalGradePartCourses(finalGradePartCourses);
			map.put("studentId", student.getId());
			List<FinalGradePartStudent> finalGradePartStudents = finalGradeService.findFinalGradePartStudents(map);
			finalGradeStudentVo.setFinalGradePartStudents(finalGradePartStudents);
			finalGradeStudentVos.add(finalGradeStudentVo);
		}

		return finalGradeStudentVos;
	}

	@RequestMapping(path = "/studentscore", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('finalgrade', 'add')")
	public FinalGradePartStudent modifyStudentScore(
			@RequestBody FinalGradePartStudentScoreForm finalGradePartStudentScoreForm) {
		Map<String, Object> map = new HashMap<>();
		map.put("facultyId", finalGradePartStudentScoreForm.getFacultyId());
		map.put("termCode", finalGradePartStudentScoreForm.getTermCode());
		map.put("courseId", finalGradePartStudentScoreForm.getCourseId());
		map.put("studentId", finalGradePartStudentScoreForm.getStudentId());
		map.put("finalGradePartCourseId", finalGradePartStudentScoreForm.getFinalGradePartCourseId());
		FinalGradePartStudent finalGradePartStudent = finalGradeService.getFinalGradePartStudent(map);
		if (finalGradePartStudent == null) {
			FinalGradePartStudent createFinalGradePartStudent = new FinalGradePartStudent();
			Student student = studentService.getStudent(finalGradePartStudentScoreForm.getStudentId());
			createFinalGradePartStudent.setStudent(student);
			FinalGradePartCourse finalGradePartCourse = finalGradeService
					.getFinalGradePartCourse(finalGradePartStudentScoreForm.getFinalGradePartCourseId());
			createFinalGradePartStudent.setFinalGradePartCourse(finalGradePartCourse);
			createFinalGradePartStudent.setScore(finalGradePartStudentScoreForm.getScore());
			return finalGradeService.createFinalGradePartStudent(createFinalGradePartStudent);
		}
		finalGradePartStudent.setScore(finalGradePartStudentScoreForm.getScore());
		return finalGradeService.updateFinalGradePartStudent(finalGradePartStudent);
	}
}
