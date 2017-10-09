package com.edu.biz.schoolroll.strategy.impl;

import org.springframework.stereotype.Component;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.schoolroll.strategy.StudentChangeStrategy;

@Component
public class ChangeMajor implements StudentChangeStrategy {
	@Override
	public Student changeStudentField(StudentChange change, Student student) {
		student.setMajor(change.getNewMajor());
		student.setStatus(StudentStatus.enable);
		student.setNo(null);
		
		return student;
	}
}
