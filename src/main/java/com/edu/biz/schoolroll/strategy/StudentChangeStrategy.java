package com.edu.biz.schoolroll.strategy;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;

public interface StudentChangeStrategy {
	public Student changeStudentField(StudentChange change, Student student);
}
