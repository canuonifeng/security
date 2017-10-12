package com.edu.biz.schoolroll.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.schoolroll.strategy.StudentChangeStrategy;

@Component
public class Back implements StudentChangeStrategy {
	@Autowired
	private StudentService studentService;
	
	@Override
	public Student changeStudentField(StudentChange change, Student student) {
		Student saveStudent = studentService.getStudent(student.getId());
		saveStudent.setStatus(StudentStatus.enable);
		return saveStudent;
	}
}
