package com.edu.biz.schoolroll.strategy.impl;

import org.springframework.stereotype.Component;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.schoolroll.strategy.StudentChangeStrategy;

@Component
public class Fired implements StudentChangeStrategy {
	@Override
	public Student changeStudentField(StudentChange change, Student student) {
		student.setStatus(StudentStatus.fired);
		
		return student;
	}
}
