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
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.entity.GradedCourse;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.TeachingJsonViews;
import com.edu.biz.teaching.entity.pojo.GradedTimeCheckForm;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.edu.biz.validgroup.Update;
import com.edu.core.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/gradedteaching")
@Api("分层教学")
public class GradedTeachingController extends BaseController<GradedTeaching> {
	@Autowired
	private GradedTeachingService gradedTeachingService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedTeaching', 'add')")
	public GradedTeaching add(@RequestBody GradedTeaching graded) {
		GradedTeaching gradedTeaching = gradedTeachingService.createGraded(graded);
		return gradedTeaching;
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
	@PreAuthorize("hasPermission('gradedCourse', 'add')")
	public void saveCourse(@RequestBody List<GradedCourseAndCourseTime> list) {
		gradedTeachingService.saveCourse(list);
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
	
	@RequestMapping(path = "/{id}/courses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedCourse', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<GradedCourseAndCourseTime> findCourses(@PathVariable Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("gradedId", id);
		return gradedTeachingService.findCourses(conditions);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedTeaching', 'edit')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public GradedTeaching edit(@PathVariable Long id, @Validated({ Update.class }) @RequestBody GradedTeaching graded) {
		graded.setId(id);
		return gradedTeachingService.updateGradedTeaching(graded);
	}
	
	@RequestMapping(path = "/{id}/times", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedTime', 'edit')")
	public void editTimes(@PathVariable Long id, @Validated({ Update.class }) @RequestBody List<GradedSchooltime> list) {
		gradedTeachingService.updateGradedTimes(id, list);
	}
	
	@RequestMapping(path = "/{id}/ranks", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedRank', 'edit')")
	public void editRanks(@PathVariable Long id, @Validated({ Update.class }) @RequestBody List<GradedRank> list) {
		gradedTeachingService.updateGradedRanks(id, list);
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

	@RequestMapping(path = "/{id}/check/{teacherId}/teachingteacher", method = RequestMethod.GET)
	public Boolean checkTeachingTeacher(Long id, Long teacherId) {
		return gradedTeachingService.checkTeachingTeacher(id, teacherId);
	}
	
	@RequestMapping(path = "/{id}/teachingbuildingroom", method = RequestMethod.GET)
	public Map<String, List<BuildingRoom>> findWeekBuildingRoom(Long id) {
		return gradedTeachingService.findWeekBuildingRoom(id);
	}
	
	@RequestMapping(path = "/gradedcourses", method = RequestMethod.GET)
	@JsonView({ TeachingJsonViews.CascadeGradedCourse.class })
	public GradedCourse getGradedCourse(@RequestParam Map<String, Object> conditions) {
		return gradedTeachingService.getGradedCourse(conditions);
	}
	
	@RequestMapping(path = "/gradedcourses/rank/{rankId}/teacher/{teacherId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedCourse', 'edit')")
	public void addGradedCourseStudents(@PathVariable Long rankId,@PathVariable Long teacherId, @RequestBody Map<String, String> studentIds) {
		List<Long> addStudentIds = new ArrayList<>();
		for (String key : studentIds.keySet()) {
			addStudentIds.add(Long.parseLong(studentIds.get(key)));
		}
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("studentIds", addStudentIds);
		List<Student> students = studentService.findStudents(conditions);
		conditions.clear();
		conditions.put("rankId", rankId);
		conditions.put("teacherId", teacherId);
		List<Student> exitStudents = studentService.findStudents(conditions);
		GradedCourse gradedCourse = gradedTeachingService.getGradedCourse(conditions);
		gradedCourse.setStudents(exitStudents);
		gradedCourse.getStudents().addAll(students);
		gradedTeachingService.updateGradedCourse(gradedCourse);
	}
	
	@RequestMapping(path = "/gradedcourses/rank/{rankId}/teacher/{teacherId}/student/{studentId}/remove", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedCourse', 'edit')")
	@JsonView({ TeachingJsonViews.CascadeGradedCourse.class })
	public void removeGradedCourseStudents(@PathVariable Long rankId,@PathVariable Long teacherId, @PathVariable Long studentId) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("rankId", rankId);
		conditions.put("teacherId", teacherId);
		GradedCourse gradedCourse = gradedTeachingService.getGradedCourse(conditions);
		Student student = studentService.getStudent(studentId);
		if(student == null){
			throw new NotFoundException("该学生不存在");
		}
		conditions.clear();
		conditions.put("rankId", rankId);
		conditions.put("teacherId", teacherId);
		List<Student> students = studentService.findStudents(conditions);
		for (int i = 0; i < students.size(); i++) {
			if(students.get(i).getId().equals(student.getId())){
				students.remove(i);
				break;
			}
		}
		gradedCourse.setStudents(students);
		gradedTeachingService.updateGradedCourse(gradedCourse);
	}
	
	@RequestMapping(path = "/gradedcourses/rank/{rankId}/teacher/{teacherId}/students", method = RequestMethod.GET)
	@JsonView({ TeachingJsonViews.CascadeStudent.class })
	public List<Student> findGradedCourseStudent(@PathVariable Long rankId,@PathVariable Long teacherId, @RequestParam Map<String, Object> map) {
		map.put("rankId", rankId);
		map.put("teacherId", teacherId);
		return studentService.findStudents(map);
	}	
	
	@RequestMapping(path = "/gradedcourses/rank/{rankId}/teacher/{teacherId}/addstudents", method = RequestMethod.GET)
	@JsonView({ TeachingJsonViews.CascadeStudent.class })
	public List<Student> findGradedCourseAddStudent(@PathVariable Long rankId,@PathVariable Long teacherId, @RequestParam Map<String, Object> map) {
		map.put("rankId", rankId);
		map.put("teacherId", teacherId);
		return gradedTeachingService.findAddStudents(map);
	}
	
	@RequestMapping(path = "/gradedcourses/rank/{rankId}/teacher/{teacherId}/classrooms", method = RequestMethod.GET)
	@JsonView({ TeachingJsonViews.CascadeStudent.class })
	public List<Classroom> findGradedClassroom(@PathVariable Long rankId,@PathVariable Long teacherId) {
		return gradedTeachingService.findGradedClassrooms(rankId, teacherId);
	}
}
