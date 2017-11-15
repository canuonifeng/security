package com.edu.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.pojo.StudentVo;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;
import com.edu.biz.teaching.service.GradedSubjectService;
import com.edu.biz.validgroup.Update;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/student")
@Api("学籍")
public class StudentController extends BaseController<Student> {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private GradedSubjectService gradedSubjectService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('student', 'add')")
	public Student add(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('student', 'edit')")
	public Student edit(@PathVariable Long id, @Validated({ Update.class }) @RequestBody Student student) {
		student.setId(id);
		return studentService.updateStudent(student);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('student', 'delete')")
	public boolean delete(@PathVariable @ApiParam(name = "id", value = "学生ID", required = true) Long id) {
		return studentService.deleteStudent(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public Student get(@PathVariable Long id) {
		Student student = new Student();
		student.setId(id);
		return studentService.getStudent(student.getId());
	}

	@RequestMapping(path = "/join/{classroomId}/classroom", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('student', 'put')")
	public Boolean joinClassroom(@PathVariable Long classroomId, @RequestBody Map<String, String> studentIds) {
		Classroom classroom = classroomService.getClassroom(classroomId);
		if(classroom.getIsAssignNum() == 1) {
			Student lastStudent = studentService.findByClassroomIdOrderByNoDesc(classroom.getId());
			String lastNo = lastStudent.getNo().substring(lastStudent.getNo().length()-2);
			int no = Integer.parseInt(lastNo);
			DecimalFormat dfInt = new DecimalFormat("00");
			for (String key : studentIds.keySet()) {
				Long id = Long.parseLong(studentIds.get(key));
				Student student = studentService.getStudent(id);
				String studentNo = classroom.getCode()+dfInt.format(no + 1);
				student.setNo(studentNo);
				student.setClassroom(classroom);
				studentService.AssignStudentNum(student);
				no++;
			}
		} else {
			for (String key : studentIds.keySet()) {
				Long id = Long.parseLong(studentIds.get(key));
				Student student = studentService.getStudent(id);
				studentService.joinClassroom(student, classroom);
			}
		}

		return true;
	}

	@RequestMapping(path = "/{classroomId}/assginnum", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('student', 'put')")
	public Boolean assginNum(@PathVariable Long classroomId, @RequestBody Map<Integer, String> studentIds) {
		Classroom classroom = classroomService.getClassroom(classroomId);
		int num = 0;
		DecimalFormat dfInt = new DecimalFormat("00");
		for (Integer i = 0; i < studentIds.size(); i++) {
			String studentNo = classroom.getCode()+dfInt.format(num + 1);
			Student student = studentService.getStudent(Long.parseLong(studentIds.get(i)));
			student.setNo(studentNo);
			studentService.updateStudent(student);
			num++;
		}
		classroom.setIsAssignNum(1);
		classroom.setLastAssignNum(num);
		classroomService.updateClassroom(classroom);
		return true;
	}

	@RequestMapping(path = "/{classroomId}/studentssort", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('student', 'put')")
	public Boolean studentSort(@RequestBody Map<Integer, String> studentIds) {
		for (Integer i = 0, seq = 1; i < studentIds.size(); i++) {
			Student student = studentService.getStudent(Long.parseLong(studentIds.get(i)));
			student.setSeq(seq);
			studentService.updateStudent(student);
			seq++;
		}
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public Page<Student> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Student> page = studentService.searchStudents(conditions, pageable);
		
		return page;
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('student', 'get')")
	public List<StudentVo> findStudents(@RequestParam Map<String, Object> conditions) {
		List<Student> list = studentService.findStudents(conditions);
		List<StudentVo> studentVos = new ArrayList<StudentVo>();
		for (Student student : list) {
			StudentVo studentVo = new StudentVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(student, studentVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("facultyId", student.getMajor().getFaculty().getId());
			map.put("grade", student.getGrade());
			List<GradedSubject> gradedSubjects = gradedSubjectService.findGradedSubjects(map);
			map.clear();
			map.put("grade", student.getGrade());
			map.put("studentId", student.getId());
			List<GradedSubjectResult> gradedSubjectResults = gradedSubjectService.findGradedSubjectResults(map);
			studentVo.setGradedSubjects(gradedSubjects);
			studentVo.setGradedSubjectResults(gradedSubjectResults);
			studentVos.add(studentVo);
		}
		return studentVos;
	}
}
