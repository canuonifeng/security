package com.edu.biz.schoolroll.strategy.impl;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.biz.schoolroll.dao.ClassroomDao;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.strategy.StudentChangeStrategy;

@Component
public class ChangeClassroom implements StudentChangeStrategy {
	@Autowired
	ClassroomDao classroomDao;
	@Override
	public Student changeStudentField(StudentChange change, Student student) {
		Classroom classroom = classroomDao.findOne(change.getNewClassroom().getId());
		if (classroom.getIsAssignNum() == 1) {
			DecimalFormat dfInt = new DecimalFormat("00");
			String studentNo = classroom.getCode()+dfInt.format(classroom.getLastAssignNum() + 1);
			student.setNo(studentNo);
			classroom.setLastAssignNum(classroom.getLastAssignNum() + 1);
			classroomDao.save(classroom);
		} else {
			student.setNo(null);
		}
		student.setClassroom(change.getNewClassroom());
		return student;
	}
}
